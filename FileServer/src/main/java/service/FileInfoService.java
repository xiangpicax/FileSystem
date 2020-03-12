package service;

import model.Fileinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileInfoService {
    Fileinfo getFileMsg(String fiid) throws Exception;

    HttpServletResponse  uploadFile(HttpServletRequest request, HttpServletResponse response, String directory) throws Exception;

    List<Fileinfo> getListMsg() throws Exception;
}
