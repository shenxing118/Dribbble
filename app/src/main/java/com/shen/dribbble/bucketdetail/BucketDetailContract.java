package com.shen.dribbble.bucketdetail;

import com.shen.dribbble.data.Shot;
import com.shen.dribbble.shots.ShotItemContract;

import java.util.List;

/**
 * Created by shen on 2016/8/2.
 */
public interface BucketDetailContract {

    interface Presenter extends ShotItemContract.Presenter {
        void loadBucketShots(int bucketId, int page);
    }

    interface View extends ShotItemContract.View {
        void showBucketShots(List<Shot> shots);
    }


}
