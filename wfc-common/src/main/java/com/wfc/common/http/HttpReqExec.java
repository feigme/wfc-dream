package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/4/26 5:02 下午
 */
public interface HttpReqExec {

    /**
     * 执行url，返回string
     *
     * @param uri
     * @param param
     *
     * @return
     */
    String exec(String uri, Map<String, Object> param);

    /**
     * 执行url，使用handler处理返回结果
     *
     * @param uri
     * @param param
     * @param handler
     */
    void exec(String uri, Map<String, Object> param, HttpRspHandler handler);

}
