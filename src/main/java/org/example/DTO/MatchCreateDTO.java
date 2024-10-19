package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MatchCreateDTO {
    private Long player1;
    private Long player2;
    private Long winner;
}
