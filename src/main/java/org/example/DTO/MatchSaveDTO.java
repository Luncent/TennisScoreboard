package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MatchSaveDTO {
    Long player1;
    Long player2;
    Long winner;
}
