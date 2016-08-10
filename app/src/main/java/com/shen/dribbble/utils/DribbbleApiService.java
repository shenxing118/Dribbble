package com.shen.dribbble.utils;

import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by shen on 2016/7/22.
 */
public interface DribbbleApiService {

    @GET("shots")
    Observable<List<Shot>> getShots(@Query("page") int page);

    @GET("https://dribbble.com/oauth/authorize?client_id=f0ae58ab371a91b3c01ae04272eb9e38a4e4e1417a3de8ddc4cfd110aa0b0f6e")
    Observable<Object> auth();

    @GET("users/{user}/shots")
    Observable<List<Shot>> getUserShots(@Path("user") String username,@Query("page") int page);

    @GET("shots/{shotId}/likes")
    Observable<List<Like>> getShotLikes(@Path("shotId") int shotId,@Query("page") int page);

    @GET("shots/{shotId}/comments")
    Observable<List<Comment>> getShotComments(@Path("shotId") int shotId, @Query("page") int page);

    @GET("shots/{shotId}/buckets")
    Observable<List<Bucket>> getShotBuckets(@Path("shotId") int shotId, @Query("page") int page);

    @GET("buckets/{bucketId}/shots")
    Observable<List<Shot>> getBucketShots(@Path("bucketId") int bucketId, @Query("page") int page);

}
