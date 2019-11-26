package com.github.wahno.fastdfsms.constant;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS("S_000"),
    /**
     * 全部成功
     */
    ALL_SUCCESS("S_001"),
    /**
     * 带数据返回的成功
     */
    SUCCESS_WITH_DATA("S_003"),
    /**
     * 失败
     */
    FAIL("F_000");

    private String code;
    ResultCodeEnum(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
