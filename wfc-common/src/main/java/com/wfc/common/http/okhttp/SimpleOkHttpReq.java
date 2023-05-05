package com.wfc.common.http.okhttp;

import com.google.common.base.Preconditions;
import com.wfc.common.http.AbsHttpReq;
import com.wfc.common.http.HttpException;
import com.wfc.common.http.HttpReq;
import com.wfc.common.http.HttpRspHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author hui.guo
 * @since 2023/4/26 6:04 下午
 */
@Slf4j
public class SimpleOkHttpReq extends AbsHttpReq {

    private OkHttpClient okHttpClient = DefaultOkHttpClientBuilder.ins().build();

    public static HttpReq build() {
        return new SimpleOkHttpReq();
    }

    @Override
    public <T> T execute(HttpRspHandler<T> handler) {
        Request request = new Request.Builder().url(this.url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Preconditions.checkNotNull(response);
            Preconditions.checkNotNull(response.body());
            return handler.handle(response.body().string());
        } catch (IOException e) {
            log.error("okHttp请求异常", e);
            throw new HttpException(e);
        }
    }

    @Override
    public <T> void asyncExecute(HttpRspHandler<T> handler) {
        Request request = new Request.Builder().url(this.url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

}
