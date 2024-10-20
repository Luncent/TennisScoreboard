package org.example.Servlets.ServletContext;

import org.example.DTO.MatchSaveDTO;
import org.example.DTO.PlayerCreateDTO;
import org.example.Exceptions.EmptyException;
import org.example.Models.ActiveMatch;
import org.example.Services.MatchService;
import org.example.Services.PlayerService;
import org.example.Services.SaveMatchService;
import org.example.Utils.SessionSupplier;
import org.example.Validators.MatchValidator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ServletContainerController implements ServletContextListener {
    private SessionFactory factory;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        MatchValidator matchValidator = new MatchValidator();
        factory = new Configuration().configure().buildSessionFactory();
        SessionSupplier supplier = new SessionSupplier(factory);
        PlayerService playerService = new PlayerService(supplier);
        MatchService matchService = new MatchService(supplier);
        SaveMatchService saveMatchService = new SaveMatchService(supplier);
        ConcurrentHashMap<UUID, ActiveMatch> matches = new ConcurrentHashMap<>();
        try {
            initializeDB(playerService,saveMatchService);
        } catch (EmptyException e) {
            throw new RuntimeException(e);
        }

        servletContextEvent.getServletContext().setAttribute("matchValidator",matchValidator);
        servletContextEvent.getServletContext().setAttribute("playerService", playerService);
        servletContextEvent.getServletContext().setAttribute("matchService", matchService);
        servletContextEvent.getServletContext().setAttribute("saveMatchService", saveMatchService);
        servletContextEvent.getServletContext().setAttribute("matches", matches);
    }

    private void initializeDB(PlayerService playerService,SaveMatchService saveMatchService) throws EmptyException {
        List<PlayerCreateDTO> players = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            PlayerCreateDTO player = new PlayerCreateDTO("player" + i);
            players.add(player);
            playerService.save(player);
        }
        for (int i = 1; i <= 10; i += 2) {
            MatchSaveDTO match = MatchSaveDTO.builder()
                    .player1(Long.valueOf(i))
                    .player2(Long.valueOf(i+1))
                    .winner(Long.valueOf(i))
                    .build();
            saveMatchService.save(match);
        }
        MatchSaveDTO match = MatchSaveDTO.builder()
                .player1(1L)
                .player2(5L)
                .winner(5L)
                .build();
        saveMatchService.save(match);
        match = MatchSaveDTO.builder()
                .player1(1L)
                .player2(3L)
                .winner(3L)
                .build();
        saveMatchService.save(match);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        factory.close();
        System.out.println("context destroyed");
    }
}
