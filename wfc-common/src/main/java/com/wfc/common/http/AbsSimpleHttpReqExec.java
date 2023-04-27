package com.wfc.common.http;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2023/4/26 5:35 下午
 */
public abstract class AbsSimpleHttpReqExec implements HttpReqExec {

    @Override
    public void exec(String uri, Map<String, Object> param, HttpRspHandler handler) {
        handler.handle(this.exec(uri, param));
    }

}
