package com.wfc.common.http.okhttp;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author hui.guo
 * @since 2023/4/27 8:48 下午
 */
public class DefaultOkHttpClientBuilder {

    private DefaultOkHttpClientBuilder() {

    }

    public static DefaultOkHttpClientBuilder ins() {
        return DefaultOkHttpClientBuilder.SingletonHolder.INSTANCE;
    }

    public OkHttpClient build() {
        return this.build(x -> x.connectTimeout(5, TimeUnit.SECONDS));
    }

    public OkHttpClient build(Consumer<OkHttpClient.Builder> consumer) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    public static class SingletonHolder {
        private static final DefaultOkHttpClientBuilder INSTANCE = new DefaultOkHttpClientBuilder();
    }

}
