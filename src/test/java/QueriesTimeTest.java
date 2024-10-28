import org.example.DTO.MatchSaveDTO;
import org.example.DTO.PlayerCreateDTO;
import org.example.Entities.Match;
import org.example.Exceptions.EmptyException;
import org.example.Services.MatchPlayerService;
import org.example.Services.MatchService;
import org.example.Services.PlayerService;
import org.example.Utils.SessionSupplier;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QueriesTimeTest {
    SessionSupplier ss;
    PlayerService serv;
    MatchService mserv;
    MatchPlayerService sserv;

    private void setupServices(SessionFactory factory){
        ss = new SessionSupplier(factory);
        serv = new PlayerService(ss);
        mserv = new MatchService(ss);
        sserv = new MatchPlayerService(ss);
    }
    private void fillData(SessionFactory factory) throws EmptyException {
        List<PlayerCreateDTO> players = new ArrayList<>();
        for (int i = 1; i <= 2000; i++) {
            PlayerCreateDTO player = new PlayerCreateDTO("player" + i);
            players.add(player);
            serv.save(player);
        }
        for (int i = 1; i <= 2000; i += 2) {
            MatchSaveDTO match = MatchSaveDTO.builder()
                    .player1(Long.valueOf(i))
                    .player2(Long.valueOf(i+1))
                    .winner(Long.valueOf(i))
                    .build();
            sserv.save(match);
        }
        MatchSaveDTO match = MatchSaveDTO.builder()
                .player1(1L)
                .player2(5L)
                .winner(5L)
                .build();
        sserv.save(match);
        match = MatchSaveDTO.builder()
                .player1(1L)
                .player2(3L)
                .winner(3L)
                .build();
        sserv.save(match);
    }

    @Test
    public void paginationTest() throws EmptyException {
        try(SessionFactory factory = new Configuration().configure().buildSessionFactory()){
            setupServices(factory);
            fillData(factory);
            try(Session session = factory.openSession()){
                Transaction tx = session.beginTransaction();
                try{


                    Long startTime = System.currentTimeMillis();
                    System.out.println(session.createQuery("FROM Match",Match.class).list());
                    Long endTime = System.currentTimeMillis();
                    System.out.println(endTime - startTime);
                    tx.commit();

                    startTime = System.currentTimeMillis();
                    System.out.println(session.createQuery("SELECT COUNT(*) FROM Match").getSingleResult());
                    endTime = System.currentTimeMillis();
                    System.out.println(endTime - startTime);
                }
                catch(Exception e){
                    tx.rollback();
                }
            }
        }
    }
}
