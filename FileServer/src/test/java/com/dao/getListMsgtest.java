package com.dao;

import model.Fileinfo;
import org.junit.Assert;
import org.junit.Test;
import service.FileInfoService;
import service.impl.FileInfoServiceImpl;

import java.util.List;

public class getListMsgtest {
    @Test
    public void getListMsgtest() {
        FileInfoService fileInfoService = new FileInfoServiceImpl();
        List<Fileinfo> listMsg = null;
        try {
            listMsg = fileInfoService.getListMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(listMsg);
    }

}
