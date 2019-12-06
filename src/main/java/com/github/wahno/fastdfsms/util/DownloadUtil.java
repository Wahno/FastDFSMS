package com.github.wahno.fastdfsms.util;

import com.github.wahno.fastdfsms.entity.dto.DownloadFile;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.exception.FileExceptionEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
public class DownloadUtil {
    public static void attachmentDownload(HttpServletResponse response, DownloadFile downloadFile)
            throws IOException, FileException {
        if (downloadFile.getFileByte() == null) {
            throw new FileException(FileExceptionEnum.FILE_NULL);
        } else {
            OutputStream out = getAttachmentDownloadStream(response,downloadFile.getFileName(),
                    (long) downloadFile.getFileByte().length);
            out.write(downloadFile.getFileByte());
            out.close();
            System.gc();
        }
    }

    public static void attachmentDownloadZip(HttpServletResponse response,
                                             List<DownloadFile> downloadFileList, String downFileName)
            throws IOException, FileException {
        Long size = 0L;
        for (DownloadFile downloadFile : downloadFileList){
            size += downloadFile.getFileByte().length;
        }
        OutputStream out = getAttachmentDownloadStream(response,downFileName,size);
        out.write(ZipUtil.getZip(downloadFileList));
        out.close();
        System.gc();
    }

    public static OutputStream getAttachmentDownloadStream(HttpServletResponse response,
                                                           String downFileName,Long fileSize)
            throws IOException {
        //以附件的形式下载
        response.setHeader("Content-Disposition", "attachment;fileName=" + downFileName);
        response.setHeader("Content-Length",String.valueOf(fileSize));
        return response.getOutputStream();
    }
}
