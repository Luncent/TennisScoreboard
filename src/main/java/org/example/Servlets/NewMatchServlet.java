package org.example.Servlets;

import org.example.DTO.MatchCreateDTO;
import org.example.DTO.PlayerCreateDTO;
import org.example.Entities.Player;
import org.example.Exceptions.EmptyException;
import org.example.Exceptions.ValidationException;
import org.example.Models.ActiveMatch;
import org.example.Services.PlayerService;
import org.example.Validators.MatchValidator;
import org.example.Validators.PlayerValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "NewMatchServlet", urlPatterns = {"/new-match"})
public class NewMatchServlet extends HttpServlet {
    private MatchValidator matchValidator;
    private PlayerService playerService;
    private ConcurrentHashMap<UUID, ActiveMatch> matches;

    @Override
    public void init() throws ServletException {
        matchValidator = (MatchValidator) getServletContext().getAttribute("matchValidator");
        playerService = (PlayerService) getServletContext().getAttribute("playerService");
        matches = (ConcurrentHashMap<UUID,ActiveMatch>)getServletContext().getAttribute("matches");
        System.out.println("new-match servlet initializing");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/new_match.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String firstPlayerName = (req.getParameter("player1")+"").trim();
        String secondPlayerName = (req.getParameter("player2")+"").trim();
        MatchCreateDTO dto  = new MatchCreateDTO(firstPlayerName,secondPlayerName);
        try{
            //сохранение в бд только при конце матча
            if(matchValidator.validateCreateDTO(dto)) {
                Player player1,player2;
                Optional<Player> optionalPlayer =  playerService.getByName(firstPlayerName);
                if(!optionalPlayer.isPresent()){
                    player1 = playerService.save(new PlayerCreateDTO(firstPlayerName));
                }
                else {player1 = optionalPlayer.get();}
                optionalPlayer = playerService.getByName(secondPlayerName);
                if(!optionalPlayer.isPresent()){
                    player2 = playerService.save(new PlayerCreateDTO(secondPlayerName));
                }
                else {player2 = optionalPlayer.get();}

                UUID newMatchID = UUID.randomUUID();
                ActiveMatch newMatch = new ActiveMatch(player1.getId(),player2.getId());
                matches.put(newMatchID,newMatch);

                resp.sendRedirect("/match-score?uuid="+newMatchID);
            }
        } catch (ValidationException ex){
            ex.printStackTrace();
            resp.setStatus(400);
            writer.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(500);
            writer.println(ex.getMessage());
        }
    }
}
