package com.github.wahno.fastdfsms.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wahno.fastdfsms.constant.ResultCodeEnum;
import com.github.wahno.fastdfsms.constant.ResultConstant;
import org.springframework.stereotype.Component;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
@Component
public class Result {

    /**
     * 响应代码
     */
    private static final String CODE = "code";
    /**
     * 响应消息
     */
    private static final String MESSAGE = "message";
    /**
     * 响应正文
     */
    private static final String CONTEXT = "context";

    public String defaultResult(Boolean flag){
        if(flag){
            return successMsg();
        }
        else {
            return failMsg();
        }
    }

    public String successMsg(){
        return successMsg("");
    }

    /**
     * 成功带消息
     * @param msg
     * @return
     */
    public String successMsg(String msg){
        return getResult(ResultCodeEnum.SUCCESS.getCode(),msg).toJSONString();
    }

    /**
     * 成功带有数据返回
     * @param object 数据
     * @return
     */
    public String successData(Object object){
        return getResult(ResultCodeEnum.SUCCESS_WITH_DATA.getCode(),object).toJSONString();
    }


    public String allSuccess(){
        return getResult(ResultCodeEnum.ALL_SUCCESS.getCode(),"").toJSONString();
    }

    public String failMsg(){
        return failMsg("");
    }

    public String failMsg(String msg){
        return getResult(ResultCodeEnum.FAIL.getCode(),msg).toJSONString();
    }

    public String toJSON(Object object) {
        return JSON.toJSONString(object);
    }



    /**
     * 获取返回报文对象
     * @param code 返回代码
     * @param object 返回正文对象
     * @return
     */
    private JSONObject getResult(String code, Object object){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE, code);
        jsonObject.put(MESSAGE, ResultConstant.CODE_MAP.get(code));
        jsonObject.put(CONTEXT,object);
        return jsonObject;
    }
}
