package com.github.wahno.fastdfsms.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.service.FileService;
import com.github.wahno.fastdfsms.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FastFileStorageClient storageClient;

    @Override
    public FileInfo upload(InputStream file, FileInfo fileInfo) {
        StorePath storePath = storageClient.uploadFile(file,fileInfo.getSize(),fileInfo.getSuffix(),FileUtil.fileInfoToFileMetaData(fileInfo));
        fileInfo.setFileGroup(storePath.getGroup());
        fileInfo.setFilePath(storePath.getPath());
        return fileInfo;
    }

    @Override
    public FileInfo upload(MultipartFile file, String ownerId) throws FileException {
        FileInfo fileInfo = FileUtil.getFileInfoFromMultipartFile(file);
        fileInfo.setOwnerId(ownerId);
        InputStream fileStream = null;
        try {
            fileStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upload(fileStream,fileInfo);
    }

    @Override
    public byte[] download(String group, String path) {
        return storageClient.downloadFile(group,path,new DownloadByteArray());
    }

    @Override
    @Async
    public Future<byte[]> asyncDownload(String group, String path) {
        return new AsyncResult<>(download(group,path));
    }

    @Override
    public byte[] download(StorePath storePath) {
        return download(storePath.getGroup(),storePath.getPath());
    }

    @Override
    public byte[] download(String fullPath) {
        return download(FileUtil.getStorePathFromFullPath(fullPath));
    }

    @Override
    public void delete(String fullPath) {
        storageClient.deleteFile(fullPath);
    }

    @Override
    public void delete(String group, String path) {
        storageClient.deleteFile(group,path);
        logger.debug("删除文件：Group:{},Path:{}",group,path);
    }

    @Override
    public Set<MetaData> getMetaData(String group, String path) {
        return storageClient.getMetadata(group,path);
    }

    @Override
    public FileInfo getFileInfo(String group, String path) {
        FileInfo fileInfo = FileUtil.fileMetaDataToFileInfo(getMetaData(group,path));
        fileInfo.setFileGroup(group);
        fileInfo.setFilePath(path);
        return fileInfo;
    }
}

