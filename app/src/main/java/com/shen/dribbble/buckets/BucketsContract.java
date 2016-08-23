package com.shen.dribbble.buckets;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.User;

import java.util.List;

/**
 * Created by shen on 2016/8/7.
 */
public interface BucketsContract {

    interface Presenter extends BasePresenter{
        void loadBuckets(int shotId, int page);

        void onAvatarClick(android.view.View view, User user);

        void onBucketItemClick(Bucket bucket);
    }

    interface View extends BaseView {
        void showBuckets(List<Bucket> buckets);

        void showUser(android.view.View view, User user);

        void showBucketDetail(Bucket bucket);
    }
}
