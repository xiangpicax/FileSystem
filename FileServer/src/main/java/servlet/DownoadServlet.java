package servlet;

import model.Fileinfo;
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
public class DownoadServlet extends HttpServlet {
    FileInfoService fileInfoService = new FileInfoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            String fileName = request.getHeader("filename");
            System.out.println("fileName:" + fileName);
            Fileinfo fileMsg = fileInfoService.getFileMsg(fileName);
            String downloadPath = fileMsg.getAddress() +"\\"+fileName+"."+fileMsg.getFiletype();
            System.out.println("downloadPath" + downloadPath);
            File file = new File(downloadPath);
            response.setContentLength((int) file.length());
            response.setHeader("Accept-Ranges", "bytes");

            int readLength = 0;

            in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
            out = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[BUFFER_SIZE];
            while ((readLength = in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }
            out.flush();
            response.addHeader("token", "hello 1");
        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("token", "hello 2");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

