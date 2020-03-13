package service.impl;

import dao.FileInfoDao;
import dao.impl.FileInfoDaoImpl;
import model.Fileinfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import service.FileInfoService;
import util.Constants;
import util.EncrpyUtil;
import util.PublicKeyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class FileInfoServiceImpl implements FileInfoService {

    private final static Logger logger = Logger.getLogger(FileInfoServiceImpl.class);

    private FileInfoDao fileInfoDao = new FileInfoDaoImpl();

    public Fileinfo getFileMsg(String fiid) throws Exception{
        Fileinfo fileinfo = fileInfoDao.selectFileMsgByFiid(fiid);
        return fileinfo;
    }

    public HttpServletResponse uploadFile(HttpServletRequest request, HttpServletResponse response, String directory) throws Exception {
        File uploadFile = new File(directory);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //指定在内存中缓存数据大小,单位为byte,这里设为1Mb
        factory.setSizeThreshold(1024 * 1024 * 100);
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 指定单个上传文件的最大尺寸,单位:字节，这里设为100Mb
        upload.setFileSizeMax(100 * 1024 * 1024);
        //指定一次上传多个文件的总尺寸,单位:字节，这里设为100Mb
        upload.setSizeMax(100 * 1024 * 1024);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items = null;
        String getfileName = null;
        // 解析request请求
        items = upload.parseRequest(request);
        if (items != null) {
            //解析表单项目
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                //如果是普通表单属性
                if (item.isFormField()) {
                    //相当于input的name属性   <input type="text" name="content">
                    String name = item.getFieldName();
                    //input的value属性
                    String value = item.getString();
                    System.out.println("属性:" + name + " 属性值:" + value);
                    getfileName = value;
                    System.out.println("@@@@@@@@@@@@@@@@@@@@"+getfileName);
                }
                //如果是上传文件
                else {
                    //数据库操作
                    //数据文件model
                    Fileinfo fileinfo = new Fileinfo();
                    String fiid = UUID.randomUUID().toString();
                    fileinfo.setFiid(fiid);
                    fileinfo.setCreateon(new Date());
                    //文件名
                    String fileName = getfileName.substring(getfileName.lastIndexOf("/") + 1, getfileName.lastIndexOf("."));
                    fileinfo.setFilename(fileName);
                    String suffix = getfileName.substring(getfileName.lastIndexOf(".") + 1);
                    fileinfo.setFiletype(suffix);
                    fileinfo.setAddress(directory);
                    String delectFileName = fileName;
                    System.out.println("###############" + fileName);
                    item.write(new File(directory, delectFileName + "." + suffix));
                    //原来文件路径与名称
                    String FilePathAndName = directory + "\\" + delectFileName + "." + suffix;
                    //删除原来的文件
                    File file = new File(FilePathAndName);
                    long length = file.length();
                    fileinfo.setFilesize(length + "KB");
                    //加密后文件路径与名称
                    String encryFilePathAndName = directory + "\\" + fiid + "." + suffix;
                    File encryFile = new File(encryFilePathAndName);
                    File dFile = new File(directory + "\\kkk." + suffix);
                    //随机AES
                    String AES = UUID.randomUUID().toString();
                    EncrpyUtil.encryptFile(file, encryFile, AES);
                    PublicKeyUtils publicKeyUtils = new PublicKeyUtils();
                    String result = publicKeyUtils.encryptByPublicKey(Constants.strPublicKey, AES);
                    fileinfo.setFilemail(result);
                    file.delete();
                    fileInfoDao.insertFileinfo(fileinfo);
                    response.addHeader("UUID", fiid);
                }
            }
        }
        return response;
    }

    public List<Fileinfo> getListMsg() throws Exception {
        List<Fileinfo> fileinfoListMsg = fileInfoDao.getListMsg();
        return fileinfoListMsg;
    }

    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            String fileName = request.getHeader("filename");
            System.out.println("fileName:" + fileName);
            Fileinfo fileMsg = getFileMsg(fileName);
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
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
