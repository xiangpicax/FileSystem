package dao.impl;

import dao.FileInfoDao;
import model.Fileinfo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileInfoDaoImpl implements FileInfoDao {
    private final static Logger logger = Logger.getLogger(FileInfoDaoImpl.class);
    public Fileinfo selectFileMsgByFiid(String fiid) {
        Fileinfo fileinfo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//            Class.forName("org.apache.derby.jdbc.AutoloadedDriver");
            conn = DriverManager.getConnection("jdbc:derby:"+System.getProperty("user.dir")+"\\Derby_data\\filesysdb");
            ps = conn.prepareStatement("select * from FILEDETAILINFO where FIID = ?");
            ps.setObject(1,fiid);
            rs = ps.executeQuery();
            while (rs.next()){
                fileinfo =  new Fileinfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getString(7));
            }
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.close();
                //数据库关闭操作
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return fileinfo;
    }

    public int insertFileinfo(Fileinfo fileinfo) {
        Connection conn = null;
        PreparedStatement ps = null;
       int result = 0;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//            Class.forName("org.apache.derby.jdbc.AutoloadedDriver");
            conn = DriverManager.getConnection("jdbc:derby:"+System.getProperty("user.dir")+"\\Derby_data\\filesysdb");
            ps = conn.prepareStatement("insert into FILEDETAILINFO VALUES(?,?,?,?,?,?,?) ");
            ps.setObject(1,fileinfo.getFiid());
            ps.setObject(2,fileinfo.getFilesize());
            ps.setObject(3,fileinfo.getFiletype());
            ps.setObject(4,fileinfo.getFilename());
            ps.setObject(5,fileinfo.getCreateon());
            ps.setObject(6,fileinfo.getAddress());
            ps.setObject(7,fileinfo.getFilemail());
            result = ps.executeUpdate();

        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                ps.close();
                conn.close();
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<Fileinfo> getListMsg() {
        List<Fileinfo> fileinfoList = new ArrayList<Fileinfo>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:derby:"+System.getProperty("user.dir")+"\\Derby_data\\filesysdb");
            ps = conn.prepareStatement("select  * from FILEDETAILINFO ORDER BY CREATEON DESC  FETCH NEXT 10 ROWS ONLY");
            rs = ps.executeQuery();
            while (rs.next()){

               Fileinfo fileinfo =  new Fileinfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getString(7));
                System.out.println(fileinfo.toString());
                fileinfoList.add(fileinfo);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                logger.info(e.getMessage());
                e.printStackTrace();
            }
        }

        return fileinfoList;
    }
}
