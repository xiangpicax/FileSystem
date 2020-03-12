package model;

import java.util.Date;

public class Fileinfo {
    public  Fileinfo(){

    }

    public Fileinfo(String fiid, String filesize, String filetype, String filename, Date createon, String address, String filemail) {
        this.fiid = fiid;
        this.filesize = filesize;
        this.filetype = filetype;
        this.filename = filename;
        this.createon = createon;
        this.address = address;
        this.filemail = filemail;
    }

    /**
     * 主键
     */
    private String fiid;
    /**
     *文件大小
     */
    private String filesize;
    /**
     *文件类型
     */
    private String filetype;
    /**
     *文件名称
     */
    private String filename;
    /**
     *创建时间
     */
    private Date createon;
    /**
     *文件位置
     */
    private String address;
    /**
     *文件加密数字信封
     */
    private String filemail;


    public String getFiid() {
        return fiid;
    }

    public String getFilesize() {
        return filesize;
    }

    public String getFiletype() {
        return filetype;
    }

    public String getFilename() {
        return filename;
    }

    public Date getCreateon() {
        return createon;
    }

    public String getAddress() {
        return address;
    }

    public String getFilemail() {
        return filemail;
    }

    public void setFiid(String fiid) {
        this.fiid = fiid;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setCreateon(Date createon) {
        this.createon = createon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFilemail(String filemail) {
        this.filemail = filemail;
    }

    @Override
    public String toString() {
        return "[{" +
                "\"fiid\":\"" + fiid + '\"' +
                ",\"filesize\":\"" + filesize + '\"' +
                ",\"filetype\":\"" + filetype + '\"' +
                ",\"filename\":\"" + filename + '\"' +
                ",\"createon\":\"" + createon +'\"' +
                ",\"address\":\"" + address.replace("\\","\\\\") + '\"' +
                ",\"filemail\":\"" + filemail + '\"' +
                "}]";
    }


}
