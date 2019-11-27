package com.github.wahno.fastdfsms.entity.dto;

import lombok.Getter;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
@Getter
public class DownloadFile {
    private byte[] fileByte;
    private String fileName;

    public DownloadFile(byte[] fileByte,String fileName){
        this.fileByte = fileByte;
        this.fileName = fileName;
    }
}
