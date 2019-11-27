package com.github.wahno.fastdfsms.facade;

import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.entity.dto.DownloadFile;
import com.github.wahno.fastdfsms.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
public interface FileFacade {
    /**
     * 文件上传
     * @param file 文件
     * @param owner 文件所有者
     * @return
     * @throws FileException
     */
    FileInfo upload(MultipartFile file, String owner) throws FileException;

    /**
     * 删除文件
     * @param id 文件ID
     * @return
     * @throws FileException
     */
    void deleteFile(String id) throws FileException;

    /**
     * 下载文件
     * @param id 文件ID
     * @return
     * @throws FileException
     */
    DownloadFile download(String id) throws FileException;

    /**
     * 文件高并发下载
     * @param idList 文件ID List
     * @return
     * @throws FileException
     */
    List<DownloadFile> download(List<String> idList) throws FileException;

    /**
     * 流式高并发
     * @param idList 文件ID List
     * @param fileName 压缩包名称
     * @param response
     * @throws FileException
     * @throws IOException
     */
    void streamAttachmentDownload(HttpServletResponse response, List<String> idList, String fileName) throws FileException, IOException;

    /**
     * 查找文件信息
     * @param id 文件ID
     * @return
     * @throws FileException
     */
    FileInfo getFileInfo(String id) throws FileException;
}
