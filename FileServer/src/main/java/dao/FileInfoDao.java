package dao;

import model.Fileinfo;

import java.util.List;

public interface FileInfoDao {
    Fileinfo selectFileMsgByFiid(String fiid);

    int insertFileinfo(Fileinfo fileinfo);

    List<Fileinfo> getListMsg();
}
