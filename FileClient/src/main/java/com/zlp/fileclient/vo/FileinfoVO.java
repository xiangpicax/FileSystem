package com.zlp.fileclient.vo;

import com.zlp.fileclient.model.Fileinfo;
import lombok.Data;


import java.io.Serializable;

@Data
public class FileinfoVO extends Fileinfo implements Serializable {
    private String  download;
    /**
     * 请求状态码
     */
    private int status;
    /**
     * 请求错误信息
     */
    private String msg;
}
