package com.dao;

import dao.FileInfoDao;
import dao.impl.FileInfoDaoImpl;
import model.Fileinfo;
import org.junit.Assert;
import org.junit.Test;

public class getMsgTest {
    @Test
    public void getMsgTest(){
        String fiid = "6408e70b-6c4f-4a77-aae5-6061acd9122a";
        FileInfoDao fileInfoDao = new FileInfoDaoImpl();
        Fileinfo fileinfo = fileInfoDao.selectFileMsgByFiid(fiid);
        Assert.assertNotNull(fileinfo);
    }
}
