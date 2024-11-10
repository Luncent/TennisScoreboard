package org.example.Models;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class MatchSet {
    Map<Long, Score> scores = new HashMap<>(); // private
    private GameState gameState = GameState.BEFORE_GAME_POINT;
    Long player1_id;
    Long player2_id;
    
    public MatchSet(Long player1_id, Long player2_id) {
        scores.put(player1_id, Score.ZERO_POINT);
        scores.put(player2_id, Score.ZERO_POINT);
        this.player1_id = player1_id;
        this.player2_id = player2_id;
    }

    private void simpleAddPoint(Long p) {
        int current;
        current = scores.get(p).ordinal();
        // i kinda doubt you decision to rely on the ordinal of the enum values.
        // if you'll decide to complicate the logic and extend enum somehow, your logic can become non-usable.
        scores.put(p, Score.values()[current + 1]);

        if (isSimpleGamePoint()) gameState = GameState.SIMPLE_GAME_POINT; // use {} even for 1 liners, decreases % of mistakes
    }

    private void addSimpleGamePoint(Long p) {
        int current;
        if (scores.get(p).compareTo(Score.THIRD_POINT) == 0) {
            gameState = GameState.VICTORY;
        } else {
            current = scores.get(p).ordinal();
            scores.put(p, Score.values()[current + 1]);
        }
        if (isEven()) {
            scores.put(player1_id, Score.EVEN);
            scores.put(player2_id, Score.EVEN);
            gameState = GameState.MORE_LESS;
        }
    }

    private void addMoreLessPoint(Long p) {
        if (isEven()) {
            if (Objects.equals(p, player1_id)){
                scores.put(player1_id,Score.MORE);
                scores.put(player2_id,Score.LESS);
            }
            if (Objects.equals(p, player2_id)) {
                scores.put(player2_id,Score.MORE);
                scores.put(player1_id,Score.LESS);
            }
        } else {
            if (Objects.equals(p, player1_id)) {
                if (scores.get(player1_id).compareTo(Score.MORE) == 0) {
                    gameState = GameState.VICTORY;
                } else {
                    scores.put(player1_id, Score.EVEN);
                    scores.put(player2_id, Score.EVEN);
                }
            }
            if (Objects.equals(p, player2_id)) {
                if (scores.get(player2_id).compareTo(Score.MORE) == 0) {
                    gameState = GameState.VICTORY;
                } else {
                    scores.put(player1_id, Score.EVEN);
                    scores.put(player2_id, Score.EVEN);
                }
            }
        }
    }


    /**
     *
     * @param p
     * @return p if setEnded, 0 otherwise
     */
    public Long addPoint(Long p){
        switch(gameState){
            case BEFORE_GAME_POINT:
                //System.out.println("before gamepoint");
                simpleAddPoint(p);
                break;
            case SIMPLE_GAME_POINT:
                //System.out.println("simple gamepoint");
                addSimpleGamePoint(p);
                break;
            case MORE_LESS:
                //System.out.println("more less point");
                addMoreLessPoint(p);
                break;
        }

        if(gameState==GameState.VICTORY){
            return p;
        }
        return 0L;
    }

    private boolean isEven() {
        return (scores.get(player1_id).compareTo(Score.EVEN)==0
                && scores.get(player2_id).compareTo(Score.EVEN)==0)
                || (scores.get(player1_id).compareTo(Score.THIRD_POINT)==0
                && scores.get(player2_id).compareTo(Score.THIRD_POINT)==0);
    }

    private boolean isSimpleGamePoint() {
        return (scores.get(player1_id).compareTo(Score.THIRD_POINT)==0
                && scores.get(player2_id).compareTo(Score.THIRD_POINT)<0)
                || (scores.get(player1_id).compareTo(Score.THIRD_POINT)<0
                && scores.get(player2_id).compareTo(Score.THIRD_POINT)==0);
    }

    public boolean isEndGame(){
        return scores.get(player1_id).compareTo(Score.THIRD_POINT)>=0
                || scores.get(player2_id).compareTo(Score.THIRD_POINT)>=0;
    }
}
