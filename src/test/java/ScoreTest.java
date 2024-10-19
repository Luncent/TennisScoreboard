import org.example.Models.ActiveMatch;
import org.example.Models.MatchSet;
import org.example.Models.Score;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ScoreTest {
    @Test
    public void test() {
        ActiveMatch match = new ActiveMatch(3L,4L);
        boolean playing = true;
        int playerScored=0;
        Random rand = new Random();
        while(playing){
            playerScored= 3+(int)(2*rand.nextDouble());
            System.out.println("Сет: "+ match.getCurrentSet()+"-- Счет по сетам:"
                    +match.getFirstPlayerWonSets()+"--"+match.getSecondPlayerWonSets());
            System.out.println("Текущий счет: Игрок1 - "+ match.getFirstPlayerScore()
                + " Игрок2 - "+ match.getSecondPlayerScore());
            System.out.println("Забил игрок "+playerScored);
            playing = !match.addPoint((long) playerScored);
        }
        System.out.println("победил "+playerScored);
    }

    @Test
    public void tt(){
        Map<Long, Score> scores = new HashMap<>();
        scores.put(1L, Score.ZERO_POINT);
        scores.put(2L, Score.ZERO_POINT);
        scores.put(1L, Score.EVEN);
        scores.put(2L, Score.EVEN);
        System.out.println(scores.getClass()==HashMap.class);
        for(Map.Entry<Long, Score> entry : scores.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue().getValue());
        }
    }
}
