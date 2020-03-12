package com.zlp.fileclient.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zlp.fileclient.model.Fileinfo;
import com.zlp.fileclient.service.FileService;
import com.zlp.fileclient.util.Constants;
import com.zlp.fileclient.util.EncrpyUtil;
import com.zlp.fileclient.util.PrivateKeyUtils;
import com.zlp.fileclient.util.SignUtils;
import com.zlp.fileclient.vo.FileinfoVO;
import com.zlp.fileclient.vo.Result;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileServiceImpl implements FileService {


    @Override
    public FileinfoVO getmsg(String fiid) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String entityStr = "";
        Fileinfo fileinfo = null;
        httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8081/show");
        uriBuilder.addParameter("fiid", fiid);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        String SID = UUID.randomUUID().toString();
        httpGet.setHeader("X-SID", SID);
        String sign = SignUtils.sign(SID, Constants.strPrivateKey);
        httpGet.setHeader("X-Signature", sign);
        response = httpClient.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();
        if (status == 403) {
            FileinfoVO fileinfoVO = new FileinfoVO();
            fileinfoVO.setStatus(403);
            fileinfoVO.setMsg("请求不合法");
            return fileinfoVO;
        }
        HttpEntity entity = response.getEntity();
        // 使用Apache提供的工具类进行转换成字符串
        entityStr = EntityUtils.toString(entity, "UTF-8");
        System.out.println(entityStr);
        JSONArray jsonArray = JSON.parseArray(entityStr);
        if (jsonArray != null && jsonArray.size() > 0) {
            List<Fileinfo> fileinfos = JSONArray.parseArray(jsonArray.toString(), Fileinfo.class);
            if (fileinfos != null && fileinfos.size() > 0) {
                fileinfo = fileinfos.get(0);
            }
        }

        System.out.println(fileinfo.toString());
        FileinfoVO fileinfoVO = new FileinfoVO();
        if (fileinfo != null) {
            BeanUtils.copyProperties(fileinfo, fileinfoVO);
            fileinfoVO.setDownload("/download?fiid=" + fileinfoVO.getFiid() + "&filemail=" + fileinfoVO.getFilemail() + "&filetype=" + fileinfoVO.getFiletype() + "&filename=" + fileinfoVO.getFilename());
        }
        return fileinfoVO;
    }

    @Override
    public ModelAndView upload(HttpServletRequest req) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String substring = "";
        ModelAndView modelAndView = new ModelAndView();
        Part filePart = req.getPart("file");
        String submittedFileName = filePart.getSubmittedFileName();
        String directory = System.getProperty("user.dir") + "\\file\\" + submittedFileName;
        filePart.write(directory);
        File file = new File(directory);
        httpClient = HttpClients.createDefault();
        // 把一个普通参数和文件上传给下面这个地址 是一个servlet
        HttpPost httpPost = new HttpPost("http://localhost:8081/upload");
        String SID = UUID.randomUUID().toString();
        httpPost.setHeader("X-SID", SID);
        String sign = SignUtils.sign(SID, Constants.strPrivateKey);
        httpPost.setHeader("X-Signature", sign);
        // 把文件转换成流对象FileBody
        FileBody bin = new FileBody(file);
        StringBody filename = new StringBody(submittedFileName, ContentType.create("text/plain", Consts.UTF_8));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("filename", filename)
                .addPart("file", bin)
                // 相当于<input type="text" name="userName" value=userName>
                .build();
        httpPost.setEntity(reqEntity);
        // 发起请求 并返回请求的响应
        response = httpClient.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        if (status == 403) {
            modelAndView.setViewName("failure");
            modelAndView.addObject("status", status);
            modelAndView.addObject("msg", "请求不合法");
            return modelAndView;
        }
        String UUID = response.getFirstHeader("UUID").toString();
        System.out.println("原始信息:" + UUID);
        substring = UUID.substring(UUID.indexOf(":") + 2);
        System.out.println("UUID信息：" + substring);
        // 获取响应对象
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            // 打印响应长度
            System.out.println("Response content length: " + resEntity.getContentLength());
            // 打印响应内容
            System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
        }
        // 销毁
        file.delete();
        EntityUtils.consume(resEntity);
        //调用查询接口
        FileinfoVO getmsg = getmsg(substring);
        if (getmsg.getStatus() == 403) {
            modelAndView.setViewName("failure");
            modelAndView.addObject("status", getmsg.getStatus());
            modelAndView.addObject("msg", "请求不合法");
            return modelAndView;
        }
        modelAndView.setViewName("success");
        modelAndView.addObject("file", getmsg);
        return modelAndView;
    }

    @Override
    public Result download(String fiid, String filemail, String filetype, String filename) throws Exception {
        Result result = new Result();
        String localFileName = System.getProperty("user.dir") + "\\file\\" + fiid + "." + filetype;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        OutputStream out = null;
        InputStream in = null;
        File downfile = null;
        HttpGet httpGet = new HttpGet("http://localhost:8081/download");
        httpGet.addHeader("fileName", fiid);
        String SID = UUID.randomUUID().toString();
        httpGet.setHeader("X-SID", SID);
        String sign = SignUtils.sign(SID, Constants.strPrivateKey);
        httpGet.setHeader("X-Signature", sign);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status == 403) {
            result.setStatus(403);
            result.setMsg("请求不合法");
            return result;
        } else if (status == 401) {
            result.setStatus(401);
            return result;
        }
        HttpEntity entity = httpResponse.getEntity();
        in = entity.getContent();
        long length = entity.getContentLength();
        if (length <= 0) {
            System.out.println("下载文件不存在！");
            return null;
        }
        File file = new File(localFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        out = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int readLength = 0;
        while ((readLength = in.read(buffer)) > 0) {
            byte[] bytes = new byte[readLength];
            System.arraycopy(buffer, 0, bytes, 0, readLength);
            out.write(bytes);
        }
        out.flush();
        PrivateKeyUtils privateKeyUtils = new PrivateKeyUtils();
        String replace = filemail.replace(" ", "+");
        String AES = privateKeyUtils.decryptionByPrivateKey(Constants.strPrivateKey, replace);
        File encryFile = new File(localFileName);
        File uploadFile = new File(System.getProperty("user.dir") + "\\file\\" + filename + "." + filetype);
        downfile = EncrpyUtil.decryptFile(encryFile, uploadFile, AES);
        result.setFile(downfile);
        result.setStatus(200);
        return result;
    }

    @Override
    public ModelAndView getListMsg() throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String entityStr = "";
        Fileinfo fileinfo = null;
        httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8081/getListMsg");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        String SID = UUID.randomUUID().toString();
        httpGet.setHeader("X-SID", SID);
        String sign = SignUtils.sign(SID, Constants.strPrivateKey);
        httpGet.setHeader("X-Signature", sign);
        response = httpClient.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();
        if (status == 403) {
            modelAndView.setViewName("failure");
            modelAndView.addObject("msg", "请求不合法");
            return modelAndView;
        }
        HttpEntity entity = response.getEntity();
        // 使用Apache提供的工具类进行转换成字符串
        entityStr = EntityUtils.toString(entity, "UTF-8");
        System.out.println(entityStr);
        JSONArray jsonArray = JSON.parseArray(entityStr);
        List<Fileinfo> fileinfoList = new ArrayList<>();
        if (jsonArray != null && jsonArray.size() > 0) {
            fileinfoList = JSONArray.parseArray(jsonArray.toString(), Fileinfo.class);
        }
        modelAndView.setViewName("showListMsg");
        modelAndView.addObject("fileinfoList", fileinfoList);
        return modelAndView;
    }
}
