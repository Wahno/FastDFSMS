package com.github.wahno.fastdfsms.service;

import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.exception.FileException;

import java.util.List;

/**
 * @author WANGHAO  2019-11-27
 * @since 0.0.1
 */
public interface FileInfoService extends BaseService<FileInfo,Long> {

    /**
     * 获取所有文件信息
     * @return
     * @throws  FileException 文件异常
     */
    List<FileInfo> findAllFileInfo() throws FileException;
}
