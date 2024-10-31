package org.example.Services;

import lombok.AllArgsConstructor;
import org.example.DTO.MatchSaveDTO;
import org.example.Entities.Match;
import org.example.Entities.Player;
import org.example.Exceptions.EmptyException;
import org.example.Repositories.MatchRepository;
import org.example.Repositories.PlayerRepository;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

@AllArgsConstructor
public class SaveMatchService {
    private SessionSupplier supplier;

    public Match save(MatchSaveDTO dto) throws EmptyException{
        try(Session session = supplier.getProxySession()){
            Transaction t = session.beginTransaction();
            try{
                PlayerRepository prepo = new PlayerRepository(session);
                MatchRepository mrepo = new MatchRepository(session);
                Optional<Player> player1 = prepo.getById(dto.getPlayer1());
                Optional<Player> player2 = prepo.getById(dto.getPlayer2());
                Optional<Player> winner = prepo.getById(dto.getWinner());
                if(!player1.isPresent() || !player2.isPresent() || !winner.isPresent()){
                    throw new EmptyException("игрок не найден");
                }
                Match match = Match.builder()
                        .player1(player1.get())
                        .player2(player2.get())
                        .winner(winner.get())
                        .build();
                match = mrepo.save(match);
                t.commit();
                return match;
            }catch (Exception ex){
                t.rollback();
                ex.printStackTrace();
                throw ex;
            }
        }
    }

}
