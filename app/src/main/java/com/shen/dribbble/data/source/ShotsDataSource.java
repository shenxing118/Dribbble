package com.shen.dribbble.data.source;

import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;

import java.util.List;

import rx.Observable;

/**
 * Created by shen on 2016/8/2.
 */
public interface ShotsDataSource {

    Observable<List<Shot>> loadShots(int page);

    Observable<List<Shot>> getUserShots(String user,int page);

    Observable<List<Like>> getShotLikes(int shotId,int page);

    Observable<List<Comment>> getShotComments(int shotId, int page);

    Observable<List<Bucket>> getShotBuckets(int shotId, int page);

    Observable<List<Shot>> getBucketShots(int bucketId, int page);

}
