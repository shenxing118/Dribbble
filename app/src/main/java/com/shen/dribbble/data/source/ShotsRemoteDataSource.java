package com.shen.dribbble.data.source;

import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.utils.Netutils;

import java.util.List;

import rx.Observable;

/**
 * Created by shen on 2016/8/2.
 */
public class ShotsRemoteDataSource implements ShotsDataSource {

    private static ShotsRemoteDataSource INSTANCE;

    public static ShotsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShotsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Shot>> loadShots(int page) {
        return Netutils.getApiService().getShots(page);
    }

    @Override
    public Observable<List<Shot>> getUserShots(String user, int page) {
        return Netutils.getApiService().getUserShots(user, page);
    }

    @Override
    public Observable<List<Like>> getShotLikes(int shotId, int page) {
        return Netutils.getApiService().getShotLikes(shotId, page);
    }

    @Override
    public Observable<List<Comment>> getShotComments(int shotId, int page) {
        return Netutils.getApiService().getShotComments(shotId, page);
    }

    @Override
    public Observable<List<Bucket>> getShotBuckets(int shotId, int page) {
        return Netutils.getApiService().getShotBuckets(shotId, page);
    }

    @Override
    public Observable<List<Shot>> getBucketShots(int bucketId, int page) {
        return Netutils.getApiService().getBucketShots(bucketId,page);
    }

}
