package com.shen.dribbble.data.source;

import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.utils.Netutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shen on 2016/8/2.
 */
public class ShotsRemoteDataSource implements ShotsDataSource{

    private static ShotsRemoteDataSource INSTANCE;

    public static ShotsRemoteDataSource getInstance() {
        if (INSTANCE == null){
            INSTANCE = new ShotsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void loadShots(int page, final LoadShotsCallback callback) {
        Call<List<Shot>> objectCall = Netutils.getApiService().getShots(page);
        objectCall.enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                List<Shot> shots = response.body();
                if (shots != null) {
                   callback.onShotsLoaded(shots);
                }
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getUserShots(String user, int page, final LoadShotsCallback callback) {
        Netutils.getApiService().getUserShots(user, page).enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                List<Shot> shots = response.body();
                if (shots != null) {
                    callback.onShotsLoaded(shots);
                }
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getShotLikes(int shotId, int page, final LoadShotLikesCallback callback) {
        Netutils.getApiService().getShotLikes(shotId,page).enqueue(new Callback<List<Like>>() {
            @Override
            public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
                List<Like> likes = response.body();
                if (likes != null){
                    callback.onLikesLoad(likes);
                }

            }

            @Override
            public void onFailure(Call<List<Like>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getShotComments(int shotId, int page, final LoadShotCommentsCallback callback) {
        Netutils.getApiService().getShotComments(shotId,page).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                if (comments != null) {
                    callback.onCommentsLoad(comments);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }


}
