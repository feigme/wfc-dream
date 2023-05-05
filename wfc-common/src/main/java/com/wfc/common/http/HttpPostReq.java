package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/5/4 8:28 下午
 */
public interface HttpPostReq extends HttpReqExec {

    HttpPostReq userAgent(String userAgent);

    HttpPostReq referer(String referer);

    HttpPostReq header(String key, String value);
    HttpPostReq header(Map<String, String> headerMap);

    HttpPostReq param(String key, String value);
    HttpPostReq param(Map<String, String> paramMap);

    HttpPostReq body(String body);

}
