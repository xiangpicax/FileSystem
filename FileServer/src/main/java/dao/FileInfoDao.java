package dao;

import model.Fileinfo;

public interface FileInfoDao {
    Fileinfo selectFileMsgByFiid(String fiid);

    int insertFileinfo(Fileinfo fileinfo);
}
