package com.github.wahno.fastdfsms.exception;

import com.alibaba.fastjson.JSON;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public class FileException  extends Exception{
    public FileException(FileExceptionEnum fileExceptionEnum){
        super(JSON.toJSONString(fileExceptionEnum));
    }
}
