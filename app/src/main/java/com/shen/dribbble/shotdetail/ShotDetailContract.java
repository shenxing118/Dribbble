package com.shen.dribbble.shotdetail;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.User;

/**
 * Created by shen on 2016/8/7.
 */
public interface ShotDetailContract {

    interface Presenter extends BasePresenter {
        void onLikesClick(int shotId);

        void onCommentsClick(int shotId);

        void onBucketsClick(int shotId);

        void onAvatarClick(User user,android.view.View view);
    }

    interface View extends BaseView<Presenter> {
        void showLikes(int shotId);

        void showComments(int shotId);

        void showBuckets(int shotId);

        void showUserUI(User user,android.view.View view);
    }
}
