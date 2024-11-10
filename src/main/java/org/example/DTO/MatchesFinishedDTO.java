package org.example.DTO;

import lombok.Builder;
import lombok.Getter;
import org.example.Entities.Match;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MatchesFinishedDTO {
    private String player1Name;
    private String player2Name;
    private String winnerName;

    public static List<MatchesFinishedDTO> convert(List<Match> matches) { 
        // such logic is usually extracted to separate layer (converters) to separate entities and dto-s from each other
        List<MatchesFinishedDTO> convertedMatches = new ArrayList<>();
        for (Match match : matches) {
            MatchesFinishedDTO dto = MatchesFinishedDTO.builder()
                    .player1Name(match.getPlayer1().getName())
                    .player2Name(match.getPlayer2().getName())
                    .winnerName(match.getWinner().getName())
                    .build();
            convertedMatches.add(dto);
        }
        return convertedMatches;
    }
}
