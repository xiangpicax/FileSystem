package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            request.setCharacterEncoding("UTF-8");

            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            String userId = request.getParameter("userId");
            String pwd = request.getParameter("pwd");
            if("admin".equals(userId) && "password".equals(pwd)){
                out.println("success");
            }else{
                out.println("fail");
            }
            out.println("<br/>");
            String servletPath = this.getServletContext().getRealPath("/");
            out.println(servletPath);
            out.close();

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

}
