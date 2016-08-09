package com.shen.dribbble.utils;

import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shen on 2016/7/22.
 */
public interface DribbbleApiService {

    @GET("shots")
    Call<List<Shot>> getShots(@Query("page") int page);

    @GET("https://dribbble.com/oauth/authorize?client_id=f0ae58ab371a91b3c01ae04272eb9e38a4e4e1417a3de8ddc4cfd110aa0b0f6e")
    Call<Object> auth();

    @GET("users/{user}/shots")
    Call<List<Shot>> getUserShots(@Path("user") String username,@Query("page") int page);

    @GET("shots/{shotId}/likes")
    Call<List<Like>> getShotLikes(@Path("shotId") int shotId,@Query("page") int page);

    @GET("shots/{shotId}/comments")
    Call<List<Comment>> getShotComments(@Path("shotId") int shotId, @Query("page") int page);

}
