package com.github.wahno.fastdfsms.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WANGHAO 2019-11-26
 * @since 0.0.1
 */
public class ResultConstant {
    public static final Map<String,String> CODE_MAP = new HashMap<String, String>(){
        {
            put("S_000","OK");
            put("S_001","All requests are complete");
            put("S_002","Some requests were not completed");
            put("S_003","Success with data");
            put("F_000","Fail");
        }
    };
}
