package servlet;

import model.Fileinfo;
import org.apache.log4j.Logger;
import service.FileInfoService;
import service.impl.FileInfoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download")
@MultipartConfig
public class DownloadServlet extends HttpServlet {
    FileInfoService fileInfoService = new FileInfoServiceImpl();
    private final static Logger logger = Logger.getLogger(DownloadServlet.class);
    /**
     * 下载文件接口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            fileInfoService.download(request,response);
        } catch (Exception e) {
            logger.info(e.getMessage());
            response.setStatus(401);
        }
    }
}

