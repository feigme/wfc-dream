package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/5/4 8:28 下午
 */
public interface HttpPostReq extends HttpReqExec {

    /**
     * 设置 http agent
     *
     * @param userAgent
     *
     * @return
     */
    HttpPostReq userAgent(String userAgent);

    /**
     * 设置 http referer
     *
     * @param referer
     *
     * @return
     */
    HttpPostReq referer(String referer);

    /**
     * 设置 http header
     *
     * @param key
     * @param value
     *
     * @return
     */
    HttpPostReq header(String key, String value);

    /**
     * 设置 http header
     *
     * @param headerMap
     *
     * @return
     */
    HttpPostReq header(Map<String, String> headerMap);

    /**
     * 设置 http 参数
     *
     * @param key
     * @param value
     *
     * @return
     */
    HttpPostReq param(String key, String value);

    /**
     * 设置 http 参数
     *
     * @param paramMap
     *
     * @return
     */
    HttpPostReq param(Map<String, String> paramMap);

    /**
     * 设置 http 请求body
     *
     * @param body
     *
     * @return
     */
    HttpPostReq body(String body);

}
