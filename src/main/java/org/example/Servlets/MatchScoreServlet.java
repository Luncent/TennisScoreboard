package org.example.Servlets;

import org.example.DTO.MatchForViewDTO;
import org.example.DTO.MatchSaveDTO;
import org.example.Exceptions.EmptyException;
import org.example.Models.ActiveMatch;
import org.example.Services.MatchService;
import org.example.Services.PlayerService;
import org.example.Services.SaveMatchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(urlPatterns = {"/match-score"})
public class MatchScoreServlet extends HttpServlet {
    private ConcurrentHashMap<UUID, ActiveMatch> matches;
    private PlayerService playerService;
    private SaveMatchService saveMatchService;

    @Override
    public void init() throws ServletException {
        matches = (ConcurrentHashMap<UUID, ActiveMatch>)getServletContext().getAttribute("matches");
        playerService = (PlayerService) getServletContext().getAttribute("playerService");
        saveMatchService = (SaveMatchService) getServletContext().getAttribute("saveMatchService");
        System.out.println("match-score servlet initializing");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            resp.setStatus(404);
            writer.write("Матч не найден");
            return;
        }
        try {
            String player1Name = playerService.getById(match.getPlayer1_id()).get().getName();
            String player2Name = playerService.getById(match.getPlayer2_id()).get().getName();
            MatchForViewDTO dto = MatchForViewDTO.builder()
                    .player1Name(player1Name)
                    .player2Name(player2Name)
                    .player1Score(match.getFirstPlayerScore())
                    .player2Score(match.getSecondPlayerScore())
                    .player1Sets(String.valueOf(match.getFirstPlayerWonSets()))
                    .player2Sets(String.valueOf(match.getSecondPlayerWonSets()))
                    .player1Id(match.getPlayer1_id())
                    .player2Id(match.getPlayer2_id())
                    .build();
            req.setAttribute("matchInfo", dto);
            resp.setStatus(200);
            req.getRequestDispatcher("WEB-INF/views/match_score.jsp").forward(req, resp);
        }
        catch (Exception ex){
            resp.setStatus(500);
            writer.write(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        Long playerScored = Long.valueOf(req.getParameter("playerScored"));
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            resp.setStatus(404);
            writer.write("Матч не найден");
            return;
        }
        if(match.addPoint(playerScored)){
            MatchSaveDTO matchSaveDTO = new MatchSaveDTO(match.getPlayer1_id(), match.getPlayer2_id(), playerScored);
            try {
                req.setAttribute("matchFinished",true);
                saveMatchService.save(matchSaveDTO);
                doGet(req,resp);
            } catch (EmptyException e) {
                resp.setStatus(404);
                writer.write(e.getMessage());
                e.printStackTrace();
            }
        }
        else{
            doGet(req,resp);
        }
    }

}
