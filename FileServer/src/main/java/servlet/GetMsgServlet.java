package servlet;

import model.Fileinfo;
import service.FileInfoService;
import service.impl.FileInfoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/show")
public class GetMsgServlet extends HttpServlet {
    private FileInfoService fileInfoService = new FileInfoServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String fiid = request.getParameter("fiid");

        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            System.out.println("##############"+fiid);
            Fileinfo fileMsg = fileInfoService.getFileMsg(fiid);
            //toString已重写成JSON字符串形式，就不在写Object->json的Util了
            out.println(fileMsg);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
