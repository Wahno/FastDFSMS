package com.github.wahno.fastdfsms.facade.impl;

import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.entity.dto.DownloadFile;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.facade.FileFacade;
import com.github.wahno.fastdfsms.service.FileInfoService;
import com.github.wahno.fastdfsms.service.FileService;
import com.github.wahno.fastdfsms.util.DownloadUtil;
import com.github.wahno.fastdfsms.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipOutputStream;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
@Service
public class FileFacadeImpl implements FileFacade {


    @Autowired
    FileService fileService;

    @Autowired
    FileInfoService fileInfoService;

    @Override
    public FileInfo upload(MultipartFile file, String owner) throws FileException {
        return fileInfoService.save(fileService.upload(file,owner));
    }

    @Override
    public void deleteFile(String id) throws FileException {
        Long idLong = Long.valueOf(id);
        FileInfo fileInfo = fileInfoService.find(idLong);
        fileService.delete(fileInfo.getFileGroup(),fileInfo.getFilePath());
        fileInfoService.delete(idLong);
    }

    @Override
    public DownloadFile download(String id) throws FileException {
        Long idLong = Long.valueOf(id);
        FileInfo fileInfo = fileInfoService.find(idLong);
        return new DownloadFile(fileService.download(fileInfo.getFileGroup(),fileInfo.getFilePath()),fileInfo.getName());
    }

    private class DownloadFileFuture {
        public Future<byte[]> fileFutureByte;
        public FileInfo fileInfo;
        public DownloadFileFuture(Future<byte[]> fileFutureByte, FileInfo fileInfo){
            this.fileFutureByte = fileFutureByte;
            this.fileInfo = fileInfo;
        }
    }
    private Queue<DownloadFileFuture> asyncDownload(List<FileInfo> fileInfoList) {
        Queue<DownloadFileFuture> downloadFileFutureQueue = new LinkedList<>();
        //并发点
        for(FileInfo fileInfo : fileInfoList){
            downloadFileFutureQueue.add(new DownloadFileFuture(
                    fileService.asyncDownload(fileInfo.getFileGroup(), fileInfo.getFilePath()), fileInfo));
        }
        return downloadFileFutureQueue;
    }
    @Override
    public List<DownloadFile> download(List<String> idList) throws FileException {
        Queue<DownloadFileFuture> downloadFileFutureList = asyncDownload(getListByIds(idList));
        List<DownloadFile> downloadFileList = new ArrayList<>();
        //汇合点
        try {
            while (!downloadFileFutureList.isEmpty()){
                DownloadFileFuture downloadFileFuture = downloadFileFutureList.poll();
                downloadFileList.add(new DownloadFile(
                        downloadFileFuture.fileFutureByte.get(),downloadFileFuture.fileInfo.getName()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return downloadFileList;
    }

    @Override
    public void streamAttachmentDownload(HttpServletResponse response, List<String> idList, String fileName) throws IOException, FileException {
        Map<String,Integer> fileNameMap = new HashMap<>(idList.size());
        List<FileInfo> fileInfoList = getListByIds(idList);
        Queue<DownloadFileFuture> downloadFileFutureList = asyncDownload(fileInfoList);
        OutputStream out = DownloadUtil.getAttachmentDownloadStream(response,fileName,getFileListFileSize(fileInfoList));
        ZipOutputStream zip = new ZipOutputStream(out);
        //汇合点
        try {
            while (!downloadFileFutureList.isEmpty()){
                DownloadFileFuture downloadFileFuture = downloadFileFutureList.poll();
                //这里直接写入response
                ZipUtil.appendZipfile(zip,fileNameMap,
                        new DownloadFile(downloadFileFuture.fileFutureByte.get(),
                                downloadFileFuture.fileInfo.getName()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        zip.close();
    }

    public List<FileInfo> getListByIds(List<String> idList) throws FileException {
        List<FileInfo> fileInfoList = new ArrayList<>();
        for(String id : idList){
            fileInfoList.add(fileInfoService.find(Long.valueOf(id)));
        }
        return fileInfoList;
    }
    private Long getFileListFileSize(List<FileInfo> fileInfoList){
        Long size = 0L;
        for (FileInfo fileInfo : fileInfoList){
            size += fileInfo.getSize();
        }
        return size;
    }

    @Override
    public FileInfo getFileInfo(String id) throws FileException {
        return fileInfoService.find(Long.valueOf(id));
    }
}
