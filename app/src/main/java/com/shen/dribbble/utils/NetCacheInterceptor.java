package com.shen.dribbble.utils;

import com.shen.dribbble.DribbbleApp;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetCacheInterceptor implements Interceptor {
    private int maxCacheSeconds;
    private Headers commonHeaders;

    public NetCacheInterceptor(int maxCacheSeconds, Headers commonHeaders) {
        this.maxCacheSeconds = maxCacheSeconds;
        this.commonHeaders = commonHeaders;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (commonHeaders != null) {
            for (String name : commonHeaders.names()) {
                builder.addHeader(name, commonHeaders.get(name));
            }
        }
        request = builder.build();
        Response originalResponse = chain.proceed(request);
        // rewrite the response headers to support cache.
        if (CommonTools.isNetworkConnected(DribbbleApp.getInstance())) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxCacheSeconds)
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + Integer.MAX_VALUE)
                    .build();
        }
    }
}