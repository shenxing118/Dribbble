package com.shen.dribbble.data.source;

import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.utils.NetUtils;

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
        return NetUtils.getApiService().getShots(page);
    }

    @Override
    public Observable<List<Shot>> getUserShots(String user, int page) {
        return NetUtils.getApiService().getUserShots(user, page);
    }

    @Override
    public Observable<List<Like>> getShotLikes(int shotId, int page) {
        return NetUtils.getApiService().getShotLikes(shotId, page);
    }

    @Override
    public Observable<List<Comment>> getShotComments(int shotId, int page) {
        return NetUtils.getApiService().getShotComments(shotId, page);
    }

    @Override
    public Observable<List<Bucket>> getShotBuckets(int shotId, int page) {
        return NetUtils.getApiService().getShotBuckets(shotId, page);
    }

    @Override
    public Observable<List<Shot>> getBucketShots(int bucketId, int page) {
        return NetUtils.getApiService().getBucketShots(bucketId,page);
    }

}
