package com.zlp.fileclient.service;

import com.zlp.fileclient.model.Fileinfo;
import com.zlp.fileclient.vo.FileinfoVO;
import com.zlp.fileclient.vo.Result;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public interface FileService {
    //获取单条信息
    FileinfoVO getmsg(String fiid) throws Exception;

    //上传
    ModelAndView upload(HttpServletRequest req) throws Exception;

    //下载
    Result download(String fiid, String filemail, String filetype, String filename) throws  Exception;

    ModelAndView getListMsg() throws Exception;
}
