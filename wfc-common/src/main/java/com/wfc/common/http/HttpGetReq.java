package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/5/4 8:28 下午
 */
public interface HttpGetReq extends HttpReqExec {

    HttpPostReq param(String key, String value);
    HttpPostReq param(Map<String, String> paramMap);
}
