package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/5/4 7:29 下午
 */
public interface HttpReq {

    /**
     * get请求
     *
     * @param url
     *
     * @return
     */
    HttpGetReq get(String url);

    /**
     * post请求
     *
     * @param url
     *
     * @return
     */
    HttpPostReq post(String url);

}
