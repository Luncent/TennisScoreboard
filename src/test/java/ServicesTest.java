import org.example.DTO.MatchSaveDTO;
import org.example.DTO.PlayerCreateDTO;
import org.example.Entities.Match;
import org.example.Entities.Player;
import org.example.Exceptions.EmptyException;
import org.example.Models.Score;
import org.example.Repositories.MatchRepository;
import org.example.Repositories.PlayerRepository;
import org.example.Services.MatchService;
import org.example.Services.PlayerService;
import org.example.Services.MatchPlayerService;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicesTest {
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
        for (int i = 1; i <= 10; i++) {
            PlayerCreateDTO player = new PlayerCreateDTO("player" + i);
            players.add(player);
            serv.save(player);
        }
        for (int i = 1; i <= 10; i += 2) {
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
    public void GetAll() throws EmptyException {
        try(SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            System.out.println(serv.getAll());
            System.out.println(mserv.getAll());
        }
    }

    @Test
    public void GetRowsCount() throws EmptyException {
        try(SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            System.out.println(mserv.getRowsCount(""));
            System.out.println(mserv.getRowsCount("1"));
        }
    }

    @Test
    public void GetPaginatedRows() throws EmptyException {
        try(SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            //System.out.println(mserv.selectPaginated("1",1,10));
            System.out.println("======================");
            System.out.println(mserv.selectPaginated("",2,10));
        }
    }

    @Test
    public void getById() throws EmptyException {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            Optional<Player> opt = serv.getById(1L);
            Optional<Match> match = mserv.getById(1L);

            System.out.println(opt.get());
            System.out.println(match.get());
        }
    }

    @Test
    public void getByName() throws EmptyException {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            //Optional<Player> player = serv.getByName("player1");
            List<Match> matches = mserv.getByPlayerName("");

            //System.out.println(player.get());
            System.out.println(matches);
        }
    }

    @Test
    public void test() throws EmptyException {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            try(Session session = factory.openSession()){
                Transaction t = session.beginTransaction();
                try{
                    System.out.println("++++++++++++++++++++++++++++");
                    MatchRepository repo = new MatchRepository(session);
                    Optional<Match> matches = repo.getById(3L);
                    System.out.println(matches.get());
                    System.out.println(repo.getAll());
                }
                catch (Exception ex){
                    t.rollback();
                    ex.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() throws EmptyException {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            setupServices(factory);
            fillData(factory);
            try(Session session = factory.openSession()){
                Transaction t = session.beginTransaction();
                try{
                    System.out.println("++++++++++++++++++++++++++++");
                    PlayerRepository repo = new PlayerRepository(session);
                    List<Player> players = repo.getAll();
                    System.out.println(players);
                }
                catch (Exception ex){
                    t.rollback();
                    ex.printStackTrace();
                }
            }
        }
    }

}

