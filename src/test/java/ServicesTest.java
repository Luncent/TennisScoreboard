import org.example.DTO.MatchCreateDTO;
import org.example.DTO.PlayerCreateDTO;
import org.example.Entities.Match;
import org.example.Entities.Player;
import org.example.Exceptions.EmptyException;
import org.example.Models.Score;
import org.example.Repositories.MatchRepository;
import org.example.Repositories.PlayerRepository;
import org.example.Services.MatchService;
import org.example.Services.PlayerService;
import org.example.Services.SaveMatchService;
import org.example.Utils.SessionSupplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicesTest {
    SessionSupplier ss;
    PlayerService serv;
    MatchService mserv;
    SaveMatchService sserv;

    private void setupServices(SessionFactory factory){
        ss = new SessionSupplier(factory);
        serv = new PlayerService(ss);
        mserv = new MatchService(ss);
        sserv = new SaveMatchService(ss);
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
            Optional<Player> player = serv.getByName("player1");
            List<Match> matches = mserv.getByPlayerName("player1");

            System.out.println(player.get());
            System.out.println(matches);
        }
    }

    @Test
    public void test(){
        String str = "   we ewd  ";
        System.out.println("ed"+str.trim()+"ed");
    }
    @Test
    public void test2(){
        System.out.println(Score.SECOND_POINT.ordinal());
    }

    private void fillData(SessionFactory factory) throws EmptyException {
        List<PlayerCreateDTO> players = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            PlayerCreateDTO player = new PlayerCreateDTO("player" + i);
            players.add(player);
            serv.save(player);
        }
        for (int i = 1; i <= 10; i += 2) {
            MatchCreateDTO match = MatchCreateDTO.builder()
                    .player1(Long.valueOf(i))
                    .player2(Long.valueOf(i+1))
                    .winner(Long.valueOf(i))
                    .build();
            sserv.create(match);
        }
        MatchCreateDTO match = MatchCreateDTO.builder()
                .player1(1L)
                .player2(5L)
                .winner(5L)
                .build();
        sserv.create(match);
        match = MatchCreateDTO.builder()
                .player1(1L)
                .player2(3L)
                .winner(3L)
                .build();
        sserv.create(match);
    }
}

