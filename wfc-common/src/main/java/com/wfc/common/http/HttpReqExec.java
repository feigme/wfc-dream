package com.wfc.common.http;

/**
 * @author hui.guo
 * @since 2023/4/26 5:02 下午
 */
public interface HttpReqExec {

    /**
     * 执行请求
     *
     * @return
     */
    String execute();

    /**
     * 执行请求
     *
     * @param handler
     * @param <T>
     *
     * @return
     */
    <T> T execute(HttpRspHandler<T> handler);

    /**
     * 异步执行
     */
    void asyncExecute();

    /**
     * 异步执行
     *
     * @param handler
     * @param <T>
     */
    <T> void asyncExecute(HttpRspHandler<T> handler);

}
