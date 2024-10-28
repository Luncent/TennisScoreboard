package org.example.Servlets.StaticContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/css/*"})
public class StaticСssServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo()!=null){
            String fileName = req.getPathInfo().substring(1);
            String path = getServletContext().getRealPath("WEB-INF/classes/static/css/"+fileName);
            System.out.println(path);
            try(BufferedReader reader = new BufferedReader(new FileReader(path))){
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                resp.setContentType("text/css;charset=UTF-8");
                resp.getWriter().write(sb.toString());
            }
            catch (Exception ex){
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
