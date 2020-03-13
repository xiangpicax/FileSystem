package servlet;

import org.apache.log4j.Logger;
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
    private final static Logger logger = Logger.getLogger(UploadServlet.class);
    private FileInfoService fileInfoService = new FileInfoServiceImpl();

    /**
     * 上传文件接口
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            //根据日期生成目录地址，存放于jetty服务器中
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = simpleDateFormat.format(new Date());
            String servletPath = this.getServletContext().getRealPath("/");
            String directory = servletPath + "\\" + date;
            System.out.println(directory);
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            fileInfoService.uploadFile(request, response, directory);
        } catch (Exception e) {
            response.setStatus(401);
            logger.info(e.getMessage());
        }
    }

}
