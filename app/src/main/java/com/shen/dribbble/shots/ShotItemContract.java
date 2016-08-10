package com.shen.dribbble.shots;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;

import java.util.List;

/**
 * Created by shen on 2016/8/2.
 */
public interface ShotItemContract {

    interface Presenter extends BasePresenter{

        void onShotItemClick(Shot shot, android.view.View view);

        void onAvatarClick(User user, android.view.View view);
    }

    interface View extends BaseView<Presenter> {

        void showShotDetail(Shot shot, SimpleDraweeView draweeView);

        void showUserDetail(User user, SimpleDraweeView draweeView);
    }


}
