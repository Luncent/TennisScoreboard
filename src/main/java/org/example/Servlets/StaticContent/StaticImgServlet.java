package org.example.Servlets.StaticContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = {"/img/*"})
public class StaticImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo()!=null){
            String fileName = req.getPathInfo().substring(1);
            String path = getServletContext().getRealPath("WEB-INF/classes/static/img/"+fileName);
            System.out.println(path);
            try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream())){
                resp.setContentType("image/jpeg");
                byte[] buffer = new byte[1024];
                while((bis.read(buffer))!=-1){
                    bos.write(buffer);
                }
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
