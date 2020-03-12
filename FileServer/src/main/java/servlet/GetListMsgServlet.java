package servlet;

import com.alibaba.fastjson.JSON;
import model.Fileinfo;
import service.FileInfoService;
import service.impl.FileInfoServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getListMsg")
public class GetListMsgServlet extends HttpServlet {
    private FileInfoService fileInfoService = new FileInfoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String fiid = request.getParameter("fiid");
        try {
            List<Fileinfo> fileinfoList = fileInfoService.getListMsg();
            //toString已重写成JSON字符串形式，就不在写Object->json的Util了
            String fileList = JSON.toJSONString(fileinfoList);
            out.println(fileList);
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
