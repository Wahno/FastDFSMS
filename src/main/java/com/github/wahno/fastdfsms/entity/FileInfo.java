package com.github.wahno.fastdfsms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.sql.Timestamp;

/**
 * 文件信息
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
@Data
@Builder
@TableName("t_file_info")
public class FileInfo {

    /**
     * 文件ID
     */
    @JSONField(name = "id")
    private String id;
    /**
     * 文件组
     */
    @JSONField(name = "file_group")
    private String fileGroup;
    /**
     * 文件路径
     */
    @JSONField(name = "file_path")
    private String filePath;
    /**
     * 文件名
     */
    @JSONField(name = "name")
    private String name;
    /**
     * 文件后缀
     */
    @JSONField(name = "suffix")
    private String suffix;
    /**
     * 文件大小
     */
    @JSONField(name = "size")
    private Long size;
    /**
     * 文件所有者
     */
    @JSONField(name = "owner_id")
    private String ownerId;
    /**
     * 上传时间
     */
    @JSONField(name = "time",format = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time;

    @Tolerate
    public FileInfo(){}
}

