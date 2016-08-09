package com.shen.dribbble.user;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.Shot;

import java.util.List;

/**
 * Created by shen on 2016/8/5.
 */
public interface UserContract {

    interface Presenter extends BasePresenter {
        void getUserShots(String user, int page);

        void onItemClick(android.view.View view, Shot shot);
    }

    interface View extends BaseView<Presenter> {
        void showUserShots(List<Shot> shots);

        void showShotDetail(android.view.View view, Shot shot);
    }

}
