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
public interface ShotsContract {

    interface Presenter extends BasePresenter{
        void loadShots(int page);

        void onShotItemClick(Shot shot,android.view.View view);

        void onAvatarClick(User user,android.view.View view);
    }

    interface View extends BaseView<Presenter> {
        void showShots(List<Shot> shots);

        void showShotDetail(Shot shot,SimpleDraweeView draweeView);

        void showUserDetail(User user,SimpleDraweeView draweeView);
    }


}
