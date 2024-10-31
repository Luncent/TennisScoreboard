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

    public static MatchSaveDTO createEmpty(){
        return MatchSaveDTO.builder().build();
    }
    public static boolean isEmpty(MatchSaveDTO obj){
        return ((obj.player1==null) || (obj.player2==null) || (obj.winner==null));
    }
}
