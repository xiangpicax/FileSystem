package service;

import model.Fileinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileInfoService {
    Fileinfo getFileMsg(String fiid);

    HttpServletResponse  uploadFile(HttpServletRequest request, HttpServletResponse response, String directory) throws Exception;
}
