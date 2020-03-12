package service;

import model.Fileinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileInfoService {
    /**
     * 获取单条文件信息
     * @param fiid
     * @return
     * @throws Exception
     */
    Fileinfo getFileMsg(String fiid) throws Exception;

    /**
     * 上传文件
     * @param request
     * @param response
     * @param directory
     * @return
     * @throws Exception
     */
    HttpServletResponse  uploadFile(HttpServletRequest request, HttpServletResponse response, String directory) throws Exception;

    /**
     * 获取10条最新上传文件信息
     * @return
     * @throws Exception
     */
    List<Fileinfo> getListMsg() throws Exception;
    
}
