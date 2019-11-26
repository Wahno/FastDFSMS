package com.github.wahno.fastdfsms.exception;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public enum FileExceptionEnum {
    //文件异常类型
    FILE_EMPTY("E_FILE_001","文件不存在"),
    FILE_ERROR("E_FILE_002","文件有误"),
    FILE_NULL("E_FILE_003","文件为空"),
    INSERT_DB_ERROR("E_FILE_004","文件信息写入数据库失败"),
    ID_IS_EMPTY("E_FILE_005","文件ID为空"),
    FILE_NAME_EMPTY("E_FILE_006","文件名为空"),
    FILE_NAME_ERROR("E_FILE_007","文件名有误");

    private String code;
    private String msg;

    FileExceptionEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public String getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
