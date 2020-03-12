package com.zlp.fileclient.controller;

import com.zlp.fileclient.service.FileService;
import com.zlp.fileclient.service.impl.FileServiceImpl;
import com.zlp.fileclient.util.DownloadUtils;

import com.zlp.fileclient.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@MultipartConfig
public class FileClientController {

    private FileService fileService = new FileServiceImpl();

    /**
     * 上传界面接口
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**
     * 上传文件接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("upload")
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            modelAndView = fileService.upload(request);
            return modelAndView;
        } catch (Exception e) {
            modelAndView.setViewName("moreFailure");
            return modelAndView;
        }
    }

    /**
     * 下载文件接口
     * @param fiid
     * @param filemail
     * @param filetype
     * @param filename
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public ModelAndView download(@RequestParam("fiid") String fiid, @RequestParam("filemail") String filemail, @RequestParam("filetype") String filetype,@RequestParam("filename") String filename,HttpServletResponse response,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");//应用程序强制下载
            Result result = fileService.download(fiid, filemail, filetype,filename);
            if(result.getStatus()==403){
                modelAndView.setViewName("failure");
                modelAndView.addObject("status",result.getStatus());
                modelAndView.addObject("msg",result.getMsg());
                return modelAndView;
            }else if(result.getStatus()==401){
                modelAndView.setViewName("moreFailure");
                return modelAndView;
            }
            DownloadUtils.download(response,result.getFile());
            modelAndView.setViewName("DownloadSuccess");
            return null;
        } catch (Exception e) {
            modelAndView.setViewName("moreFailure");
            return  modelAndView;
        }

    }

    /**
     * 获取最近10条信息接口
     * @return
     */
    @RequestMapping("getlistmsg")
public ModelAndView getListMsg(){
     ModelAndView modelAndView = new ModelAndView();
    try {
        modelAndView = fileService.getListMsg();
    } catch (Exception e) {
        modelAndView.setViewName("moreFailure");
        return modelAndView;
    }
    return  modelAndView;
}
}
