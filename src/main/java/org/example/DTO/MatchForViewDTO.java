package org.example.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MatchForViewDTO {
    private String player1Name;
    private String player2Name;
    private String player1Sets;
    private String player2Sets;
    private String player1Score;
    private String player2Score;
    private Long player1Id;
    private Long player2Id;
}
