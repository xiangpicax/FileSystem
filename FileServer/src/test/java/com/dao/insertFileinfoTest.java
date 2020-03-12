package com.dao;

import dao.FileInfoDao;
import dao.impl.FileInfoDaoImpl;
import model.Fileinfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class insertFileinfoTest {
    @Test
    public void insertFileinfoTest(){
        FileInfoDao fileInfoDao = new FileInfoDaoImpl();
        Fileinfo fileinfo = new Fileinfo();
        String uuid = UUID.randomUUID().toString();
        fileinfo.setFiid(uuid);
        fileinfo.setFilename("测试名");
        fileinfo.setFiletype("txt");
        fileinfo.setFilesize("1788KB");
        fileinfo.setAddress("c:\\users\\admin");
        fileinfo.setCreateon(new Date());
        fileinfo.setFilemail("asdjhguyqwhjbuysguyfuaysgf");
        int i = fileInfoDao.insertFileinfo(fileinfo);
        Assert.assertEquals(i,1);
    }
}
