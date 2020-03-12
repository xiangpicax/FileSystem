package com.zlp.fileclient.service;

import com.zlp.fileclient.model.Fileinfo;
import com.zlp.fileclient.vo.FileinfoVO;
import com.zlp.fileclient.vo.Result;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public interface FileService {
    /**
     * 获取单条信息
     * @param fiid
     * @return
     * @throws Exception
     */
    FileinfoVO getmsg(String fiid) throws Exception;

    /**
     * 上传信息
     * @param req
     * @return
     * @throws Exception
     */
    ModelAndView upload(HttpServletRequest req) throws Exception;

    /**
     * 下载文件
     * @param fiid
     * @param filemail
     * @param filetype
     * @param filename
     * @return
     * @throws Exception
     */
    Result download(String fiid, String filemail, String filetype, String filename) throws  Exception;

    /**
     * 获取10条最新上传文件信息
     * @return
     * @throws Exception
     */
    ModelAndView getListMsg() throws Exception;
}
