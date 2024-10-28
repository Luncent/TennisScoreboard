package org.example.Servlets;

import org.example.DTO.MatchesFinishedDTO;
import org.example.Services.MatchService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/matches"})
public class MatchesServlet extends HttpServlet {
    private MatchService matchService;
    private final int matchesOnPage = 6;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        matchService = (MatchService) getServletContext().getAttribute("matchService");
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        System.out.println("matches servlet initializing");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        WebContext context = new WebContext(req,resp,getServletContext());
        String nameFilter = req.getParameter("filter_by_player_name");
        String pageParam = req.getParameter("page");

        String currentPage = (pageParam != null && !pageParam.isEmpty()) ? pageParam : "1";
        int currentPageNumber = 1;
        try{
             currentPageNumber = Integer.parseInt(currentPage);
        }catch (Exception e){}

        try{
            List<MatchesFinishedDTO> dtomatches;
            double pagesNumber;
            if(nameFilter != null && !nameFilter.isEmpty()){
                pagesNumber = (double) matchService.getRowsCount(nameFilter).intValue() /matchesOnPage;
                pagesNumber = pagesNumber>(int)pagesNumber?(int)pagesNumber+1:pagesNumber;
                dtomatches = MatchesFinishedDTO
                        .convert(matchService.selectPaginated(nameFilter, currentPageNumber, matchesOnPage));
            }
            else{
                pagesNumber = (double) matchService.getRowsCount("").intValue() /matchesOnPage;
                pagesNumber = pagesNumber>(int)pagesNumber?(int)pagesNumber+1:pagesNumber;
                dtomatches = MatchesFinishedDTO
                        .convert(matchService.selectPaginated("",currentPageNumber,matchesOnPage));
            }

            context.setVariable("nameFilter",nameFilter);
            context.setVariable("currentPage", currentPageNumber);
            context.setVariable("pagesNumber", (int)pagesNumber);
            context.setVariable("matches", dtomatches);
            String htmlContent = templateEngine.process("matches", context);
            resp.getWriter().write(htmlContent);
        }
        catch(Exception e){
            context.setVariable("error",e.getMessage());
            resp.setStatus(500);
            String htmlContent = templateEngine.process("matches", context);
            resp.getWriter().write(htmlContent);
            out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
