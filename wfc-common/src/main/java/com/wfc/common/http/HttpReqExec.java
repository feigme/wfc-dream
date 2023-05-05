package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/4/26 5:02 下午
 */
public interface HttpReqExec {

    String execute();

    <T> T execute(HttpRspHandler<T> handler);

    void asyncExecute();

    <T> void asyncExecute(HttpRspHandler<T> handler);

}
