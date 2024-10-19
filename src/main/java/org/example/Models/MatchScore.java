package org.example.Models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MatchScore {
    List<MatchSet> matchSets = new ArrayList<>();

    public MatchScore() {

    }
}

/*
        if(!isEndGame()) {
            if (p == 1) {
                current = player1Score.ordinal();
                player1Score = Score.values()[current + 1];
            }
            if (p == 2) {
                current = player2Score.ordinal();
                player2Score = Score.values()[current + 1];
            }

        } else if (isSimpleGamePoint()) {
            if (p == 1) {
                if(player1Score.compareTo(Score.THIRD_POINT)==0){
                    return 1;
                }
                else {
                    current = player1Score.ordinal();
                    player1Score = Score.values()[current + 1];
                }
            }
            if (p == 2) {
                if(player2Score.compareTo(Score.THIRD_POINT)==0){
                    return 2;
                }
                else {
                    current = player2Score.ordinal();
                    player2Score = Score.values()[current + 1];
                }
            }
            if(isEven()){
                player1Score=Score.EVEN;
                player2Score=Score.EVEN;
            }
        } else if (isEven()) {
            if (p == 1) {
                player1Score = Score.MORE;
                player2Score = Score.LESS;
            }
            if (p == 2) {
                player1Score = Score.LESS;
                player2Score = Score.MORE;
            }
        }
        else {
            if (p == 1) {
                if(player1Score.compareTo(Score.MORE)==0){
                    return 1;
                }
                else {
                    player1Score = Score.EVEN;
                    player2Score = Score.EVEN;
                }
            }
            if (p == 2) {
                if(player2Score.compareTo(Score.MORE)==0){
                    return 2;
                }
                else {
                    player1Score = Score.EVEN;
                    player2Score = Score.EVEN;
                }
            }
        }*/
