package com.github.wahno.fastdfsms.service;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
public interface FileService {
    /**
     * 上传文件到FastDFS 同时上传文件元数据
     * @param file 文件流
     * @param fileInfo 文件信息
     * @return
     */
    FileInfo upload(InputStream file, FileInfo fileInfo);

    /**
     * 从MultipartFile获取文件元数据后组装成FileInfo调用upload(InputStream file, FileInfo fileInfo)上传文件
     * @param file
     * @param ownerId
     * @return
     */
    FileInfo upload(MultipartFile file, String ownerId) throws FileException;

    /**
     * 连接FastDFS下载文件
     * @param group 文件组
     * @param path 文件路径
     * @return
     */
    byte[] download(String group,String path);

    /**
     * 连接FastDFS下载文件
     * @param group 文件组
     * @param path 文件路径
     * @return
     */
    Future<byte[]> asyncDownload(String group, String path);

    /**
     * 调用download(String group,String path)下载文件
     * @param storePath
     * @return
     */
    byte[] download(StorePath storePath);

    /**
     * 把fullPath拆分后调用download(String group,String path)下载文件
     * @param fullPath
     * @return
     */
    byte[] download(String fullPath);

    /**
     * 把fullPath拆分后调用delete(String group,String path)删除文件
     * @param fullPath
     */
    void delete(String fullPath);

    /**
     * 删除文件
     * @param group 文件组
     * @param path 文件路径
     */
    void delete(String group,String path);

    /**
     * 从FastDFS获取文件元数据
     * @param group
     * @param path
     * @return
     */
    Set<MetaData> getMetaData(String group, String path);

    /**
     * 从FastDFS获取文件元数据并转换成FileInfo
     * @param group
     * @param path
     * @return
     */
    FileInfo getFileInfo(String group,String path);
}
