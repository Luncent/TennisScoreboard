package org.example.Servlets;

import org.example.DTO.MatchForViewDTO;
import org.example.DTO.MatchSaveDTO;
import org.example.Exceptions.EmptyException;
import org.example.Exceptions.NotFoundException;
import org.example.Models.ActiveMatch;
import org.example.Services.ActiveMatchService;
import org.example.Services.SaveMatchService;
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

@WebServlet(urlPatterns = {"/match-score"})
public class MatchScoreServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private ActiveMatchService activeMatchService;

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        activeMatchService = (ActiveMatchService) getServletContext().getAttribute("activeMatchService");
        System.out.println("match-score servlet initializing");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        WebContext context = new WebContext(req,resp,getServletContext());
        String html;
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        try {
            MatchForViewDTO dto = activeMatchService.getMatchForView(matchId);
            context.setVariable("matchInfo", dto);
            html = templateEngine.process("match-score",context);
            if(req.getAttribute("matchFinished")!=null){
                activeMatchService.removeMatch(matchId);
            }
            writer.write(html);
        }
        catch (NotFoundException ex){
            resp.setStatus(404);
            context.setVariable("error","Матч не найден");
            html = templateEngine.process("match-score",context);
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
        try{
            MatchSaveDTO dto = activeMatchService.addPoint(matchId,playerScored);
            if(!MatchSaveDTO.isEmpty(dto)){
                req.setAttribute("matchFinished",true);
            }
            doGet(req,resp);
        } catch (NotFoundException e) {
            resp.setStatus(404);
            context.setVariable("error","Матч не найден");
            html = templateEngine.process("match-score",context);
            writer.write(html);
        } catch (Exception e) {
            resp.setStatus(500);
            context.setVariable("error",e.getMessage());
            html = templateEngine.process("match-score",context);
            writer.write(html);
            e.printStackTrace();
        }
    }

}
