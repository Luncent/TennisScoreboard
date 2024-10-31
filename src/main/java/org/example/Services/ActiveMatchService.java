package org.example.Services;

import lombok.AllArgsConstructor;
import org.example.DTO.MatchForViewDTO;
import org.example.DTO.MatchSaveDTO;
import org.example.Exceptions.NotFoundException;
import org.example.Models.ActiveMatch;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public class ActiveMatchService {
    private ConcurrentHashMap<UUID, ActiveMatch> matches;
    private final PlayerService playerService;
    private final SessionSupplier supplier;
    private final SaveMatchService matchPlayerService;

    public MatchForViewDTO getMatchForView(UUID matchId) throws NotFoundException, Exception {
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            System.out.println("Exception match not found");
            throw new NotFoundException("Матч не найден");
        }
        try (Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try {
                String player1Name = playerService.getById(match.getPlayer1_id()).get().getName();
                String player2Name = playerService.getById(match.getPlayer2_id()).get().getName();
                MatchForViewDTO dto = MatchForViewDTO.builder()
                        .player1Name(player1Name)
                        .player2Name(player2Name)
                        .player1Score(match.getFirstPlayerScore())
                        .player2Score(match.getSecondPlayerScore())
                        .player1Sets(match.getFirstPlayerWonSets())
                        .player2Sets(match.getSecondPlayerWonSets())
                        .player1Id(match.getPlayer1_id())
                        .player2Id(match.getPlayer2_id())
                        .currentSet(match.getCurrentSet())
                        .build();
                return dto;
            }
            catch (Exception ex){
                ex.printStackTrace();
                t.rollback();
                throw ex;
            }
        }
    }

    public MatchSaveDTO addPoint(UUID matchId, Long playerScored) throws NotFoundException, Exception {
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            System.out.println("Exception match not found");
            throw new NotFoundException("Матч не найден");
        }
        if(match.addPoint(playerScored)){
            MatchSaveDTO matchSaveDTO = new MatchSaveDTO(match.getPlayer1_id(), match.getPlayer2_id(), playerScored);
            matchPlayerService.save(matchSaveDTO);
            return matchSaveDTO;
        }
        else return MatchSaveDTO.createEmpty();
    }

    public void removeMatch(UUID matchId){
        matches.remove(matchId);
        System.out.println("match deleted from active matches collection");
    }
}
