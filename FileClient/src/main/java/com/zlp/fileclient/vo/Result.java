package com.zlp.fileclient.vo;

import lombok.Data;

import java.io.File;

@Data
public class Result {

    private File file;

    private int status;

    private String msg;
}
