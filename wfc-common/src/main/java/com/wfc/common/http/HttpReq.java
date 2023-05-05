package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/5/4 7:29 下午
 */
public interface HttpReq {

    HttpGetReq get(String url);

    HttpPostReq post(String url);

}
