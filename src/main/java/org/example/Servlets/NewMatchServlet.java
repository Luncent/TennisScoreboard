package org.example.Servlets;

import org.example.DTO.PlayerCreateDTO;
import org.example.Exceptions.ValidationException;
import org.example.Validators.PlayerValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewMatchServlet", urlPatterns = {"/new-match"})
public class NewMatchServlet extends HttpServlet {
    private PlayerValidator playerValidator;

    @Override
    public void init() throws ServletException {
        playerValidator = (PlayerValidator) getServletContext().getAttribute("playerValidator");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/new_match.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayerCreateDTO player1DTO = new PlayerCreateDTO(req.getParameter("player1")+"");
        PlayerCreateDTO player2DTO = new PlayerCreateDTO(req.getParameter("player2")+"");
        try{
            playerValidator.validate(player1DTO);
            playerValidator.validate(player2DTO);
        } catch (ValidationException ex){
            resp.setStatus(400);
            req.setAttribute("errorMessage","Поля неправильно заполены");
            doGet(req,resp);
        }

    }
}
