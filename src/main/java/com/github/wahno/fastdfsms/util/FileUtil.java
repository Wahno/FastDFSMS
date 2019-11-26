package com.github.wahno.fastdfsms.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.wahno.fastdfsms.entity.FileInfo;
import com.github.wahno.fastdfsms.exception.FileException;
import com.github.wahno.fastdfsms.exception.FileExceptionEnum;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件工具
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public class FileUtil {
    private static final String SUFFIX_SEPARATOR = ".";
    private static final String PATH_SEPARATOR = "/";

    /**
     * 获取文件后缀名
     * @param fileName 文件名 如：test.txt
     * @return 后缀名 如：.txt
     * @throws FileException
     */
    public static String getFileExtName(String fileName) throws FileException {
        if(StringUtils.hasText(fileName)){
            try {
                int index = fileName.lastIndexOf(SUFFIX_SEPARATOR);
                return fileName.substring(index);
            }catch (StringIndexOutOfBoundsException e){
                throw new FileException(FileExceptionEnum.FILE_NAME_ERROR);
            }
        }
        else {
            throw new FileException(FileExceptionEnum.FILE_NAME_EMPTY);
        }
    }

    /**
     * 获取文件名
     * @param fileName 文件名 如：test.txt
     * @return 文件名 如：test
     * @throws FileException
     */
    public static String getFileOriginName(String fileName) throws FileException {
        if(StringUtils.hasText(fileName)){
            try {
                int index = fileName.lastIndexOf(SUFFIX_SEPARATOR);
                return fileName.substring(0,index);
            }catch (StringIndexOutOfBoundsException e){
                throw new FileException(FileExceptionEnum.FILE_NAME_ERROR);
            }
        }
        else {
            throw new FileException(FileExceptionEnum.FILE_NAME_EMPTY);
        }
    }

    /**
     * 文件元数据转FileInfo
     * @param metaDataSet 文件元数据
     * @return FileInfo
     */
    public static FileInfo fileMetaDataToFileInfo(Set<MetaData> metaDataSet){
        JSONObject jsonObject = new JSONObject();
        for(MetaData metaData:metaDataSet){
            jsonObject.put(metaData.getName(),metaData.getValue());
        }
        return JSONObject.parseObject(jsonObject.toJSONString(),FileInfo.class);
    }


    public enum FileMetaEnum {
        //这个枚举类对应 FileInfo JSON化之后的字段名
        name,size,owner_id,suffix,time
    }
    /**
     * FileInfo转文件元数据
     * @param fileInfo 文件信息
     * @return Set<MetaData>
     */
    public static Set<MetaData> fileInfoToFileMetaData(FileInfo fileInfo){
        Set<MetaData> metaDataSet = new HashSet<>();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(fileInfo));
        for(FileMetaEnum fileMeta: FileMetaEnum.values()){
            String key = fileMeta.name();
            metaDataSet.add(new MetaData(key,jsonObject.getString(key)));
        }
        return metaDataSet;
    }

    /**
     * 从MultipartFile获取FileInfo
     * @param file 文件
     * @return FileInfo->{name,size,suffix,timestamp}
     * @throws FileException
     */
    public static FileInfo getFileInfoFromMultipartFile(MultipartFile file) throws FileException {
        return FileInfo.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .suffix(getFileExtName(file.getOriginalFilename()))
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    /**
     * 从文件完整路径获取 StorePath
     * @param fullPath 完整路径
     * @return StorePath
     */
    public static StorePath getStorePathFromFullPath(String fullPath){
        if(!StringUtils.hasText(fullPath)){
            return null;
        }
        //fullPath中group和path以"/"分隔
        //注意：path中也有"/" 这里需要找第一个"/"
        int i = fullPath.indexOf(PATH_SEPARATOR);
        return new StorePath(fullPath.substring(0, i),fullPath.substring(i + 1));
    }
}
