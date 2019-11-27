package com.github.wahno.fastdfsms.util;

import com.github.wahno.fastdfsms.entity.dto.DownloadFile;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.exception.FileExceptionEnum;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
public class ZipUtil {
    /**
     * 文件压缩 Zip
     * @param fileList 文件列表
     * @return
     * @throws IOException
     * @throws FileException
     */
    public static byte[] getZip(List<DownloadFile> fileList) throws IOException, FileException {
        System.gc();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(bos);
        Map<String,Integer> fileNameMap = new HashMap<>(fileList.size()/2);
        for (DownloadFile downloadFile:fileList){
            appendZipfile(zip,fileNameMap,downloadFile);
        }
        zip.close();
        return bos.toByteArray();
    }

    /**
     * 将文件添加到zip流中
     * @param zip zip流
     * @param fileNameMap 文件名重复map
     * @param downloadFile 要添加的文件
     * @throws FileException
     * @throws IOException
     */
    public static void appendZipfile(ZipOutputStream zip, Map<String,Integer> fileNameMap, DownloadFile downloadFile)
            throws FileException, IOException {
        String originFileName = downloadFile.getFileName();
        if (downloadFile.getFileByte() == null) {
            throw new FileException(FileExceptionEnum.FILE_NULL);
        }
        String fileName;
        if (!fileNameMap.containsKey(downloadFile.getFileName())){
            fileName = originFileName;
            fileNameMap.put(originFileName,1);
        }
        else {
            fileName = FileUtil.getFileOriginName(originFileName)
                    +"("+fileNameMap.get(originFileName)+")" +FileUtil.getFileExtName(originFileName);
            fileNameMap.put(originFileName,fileNameMap.get(originFileName)+1);
        }
        ZipEntry entry = new ZipEntry(fileName);
        entry.setSize(downloadFile.getFileByte().length);
        zip.putNextEntry(entry);
        zip.write(downloadFile.getFileByte());
        zip.closeEntry();
    }
}
