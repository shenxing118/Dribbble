package com.shen.dribbble.shotdetail;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;

/**
 * Created by shen on 2016/8/7.
 */
public interface ShotDetailContract {

    interface Presenter extends BasePresenter {
        void onLikesClick(int shotId);

        void onCommentsClick(int shotId);
    }

    interface View extends BaseView<Presenter> {
        void showLikes(int shotId);

        void showComments(int shotId);
    }
}
