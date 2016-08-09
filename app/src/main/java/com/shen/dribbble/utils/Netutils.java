package com.shen.dribbble.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shen.dribbble.DribbbleApp;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shen on 2016/7/22.
 */
public class Netutils {

    public static final String BASE_URL = "https://api.dribbble.com/v1/";

    static Retrofit retrofit;

    private static void createRetrofit() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer aa17cd61491bd476696eb81077483cf9ca84e462826795cf6cf52c27ea860aac")
                        .build();
                return chain.proceed(request);
            }
        };

        //设置缓存路径
        File httpCacheDirectory = new File(DribbbleApp.getInstance().getCacheDir(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .addInterceptor(new LocalCacheInterceptor(360, null))
                .addNetworkInterceptor(new NetCacheInterceptor(360, null))
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static DribbbleApiService getApiService() {
        if (retrofit == null) {
            createRetrofit();
        }
        return retrofit.create(DribbbleApiService.class);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit();
        }
        return retrofit;
    }
}
