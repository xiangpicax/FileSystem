package servlet;

import service.FileInfoService;
import service.impl.FileInfoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private FileInfoService fileInfoService = new FileInfoServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date=simpleDateFormat.format(new Date());
        String servletPath = this.getServletContext().getRealPath("/");
        String directory = servletPath+"\\"+date;
        System.out.println(directory);
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            fileInfoService.uploadFile(request, response,directory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}