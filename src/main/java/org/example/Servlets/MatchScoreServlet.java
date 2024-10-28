package org.example.Servlets;

import org.example.DTO.MatchForViewDTO;
import org.example.DTO.MatchSaveDTO;
import org.example.Exceptions.EmptyException;
import org.example.Models.ActiveMatch;
import org.example.Services.PlayerService;
import org.example.Services.MatchPlayerService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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
    private MatchPlayerService saveMatchService;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        matches = (ConcurrentHashMap<UUID, ActiveMatch>)getServletContext().getAttribute("matches");
        playerService = (PlayerService) getServletContext().getAttribute("playerService");
        saveMatchService = (MatchPlayerService) getServletContext().getAttribute("saveMatchService");
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        System.out.println("match-score servlet initializing");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        WebContext context = new WebContext(req,resp,getServletContext());
        String html;
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            resp.setStatus(404);
            context.setVariable("error","Матч не найден");
            html = templateEngine.process("match-score",context);
            writer.write(html);
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
                    .player1Sets(match.getFirstPlayerWonSets())
                    .player2Sets(match.getSecondPlayerWonSets())
                    .player1Id(match.getPlayer1_id())
                    .player2Id(match.getPlayer2_id())
                    .currentSet(match.getCurrentSet())
                    .build();
            context.setVariable("matchInfo", dto);
            html = templateEngine.process("match-score",context);
            if(req.getAttribute("matchFinished")!=null){
                matches.remove(matchId);
                System.out.println("match deleted from active matches collection");
            }
            writer.write(html);
        }
        catch (Exception ex){
            resp.setStatus(500);
            context.setVariable("error",ex.getMessage());
            html = templateEngine.process("match-score",context);
            writer.write(html);
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        WebContext context = new WebContext(req,resp,getServletContext());
        String html;
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        Long playerScored = Long.valueOf(req.getParameter("playerScored"));
        ActiveMatch match = matches.get(matchId);
        if(match==null){
            resp.setStatus(404);
            context.setVariable("error","Матч не найден");
            html = templateEngine.process("match-score",context);
            writer.write(html);
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
                context.setVariable("error",e.getMessage());
                html = templateEngine.process("match-score",context);
                writer.write(html);
                e.printStackTrace();
            }
        }
        else{
            doGet(req,resp);
        }
    }

}
