package com.shen.dribbble.utils;

import com.shen.dribbble.DribbbleApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LocalCacheInterceptor implements Interceptor {

    private int maxCacheSeconds;
    private Headers commonHeaders;

    public LocalCacheInterceptor(int maxCacheSeconds, Headers commonHeaders) {
        this.maxCacheSeconds = maxCacheSeconds;
        this.commonHeaders = commonHeaders;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        CacheControl cacheControl = new CacheControl.Builder().maxAge(maxCacheSeconds, TimeUnit.SECONDS)
                .maxStale(0, TimeUnit.SECONDS).build();
        Request request = chain.request();
        Request.Builder builder = request.newBuilder().cacheControl(cacheControl);
        if (commonHeaders != null) {
            for (String name : commonHeaders.names()) {
                builder.addHeader(name, commonHeaders.get(name));
            }
        }
        request = builder.build();
        if (CommonTools.isNetworkConnected(DribbbleApp.getInstance())) {
            try {
                Response response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //if request failed. always load in cache
        cacheControl = new CacheControl.Builder().maxAge(Integer.MAX_VALUE, TimeUnit.SECONDS).maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
        request = builder.cacheControl(cacheControl).build();
        return chain.proceed(request);
    }
}