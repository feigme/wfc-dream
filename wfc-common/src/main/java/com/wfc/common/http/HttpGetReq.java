package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/5/4 8:28 下午
 */
public interface HttpGetReq extends HttpReqExec {

    /**
     * 请求参数
     *
     * @param key
     * @param value
     *
     * @return
     */
    HttpPostReq param(String key, String value);

    /**
     * 请求参数
     *
     * @param paramMap
     *
     * @return
     */
    HttpPostReq param(Map<String, String> paramMap);
}
