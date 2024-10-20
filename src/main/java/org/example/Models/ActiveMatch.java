package org.example.Models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActiveMatch {
    private final List<MatchSet> matchSets = new ArrayList<>(3);
    private final Map<Long, Integer> playersVictorySets = new HashMap<>();
    private int currentSet = 0;
    @Getter
    private final Long player1_id;
    @Getter
    private final Long player2_id;
    private final int setsForVictory = 1;   //2 for win then field = 1
    
    public ActiveMatch(Long player1_id, Long player2_id) {
        matchSets.add(new MatchSet(player1_id, player2_id));
        matchSets.add(new MatchSet(player1_id, player2_id));
        playersVictorySets.put(player1_id, 0);
        playersVictorySets.put(player2_id, 0);
        this.player1_id = player1_id;
        this.player2_id = player2_id;
    }

    public boolean addPoint(Long player_id) {
        Long result = matchSets.get(currentSet).addPoint(player_id);
        if(result!=0){
            if(playersVictorySets.get(player_id)==setsForVictory){
                playersVictorySets.compute(player_id, (k, temp) -> temp + 1);
                return true;
            }
            else {
                playersVictorySets.compute(player_id, (k, temp) -> temp + 1);
                if(currentSet==setsForVictory){
                    matchSets.add(new MatchSet(player1_id, player2_id));
                    currentSet+=1;
                }
                else currentSet++;
            }
        }
        return false;
    }

    public String getFirstPlayerScore(){
        return matchSets.get(currentSet).getScores().get(player1_id).getValue();
    }
    public String getSecondPlayerScore(){
        return matchSets.get(currentSet).getScores().get(player2_id).getValue();
    }
    public int getFirstPlayerWonSets(){
       return playersVictorySets.get(player1_id);
    }
    public int getSecondPlayerWonSets(){
        return playersVictorySets.get(player2_id);
    }
    public int getCurrentSet() {
        return currentSet+1;
    }
}
