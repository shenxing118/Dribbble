package com.shen.dribbble.likes;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;

import java.util.List;

/**
 * Created by shen on 2016/8/7.
 */
public interface LikesContract {

    interface Presenter extends BasePresenter{
        void loadLikes(int shotId, int page);

        void onItemClick(android.view.View view, User user);
    }

    interface View extends BaseView<Presenter> {
        void showLikes(List<Like> likes);

        void showUser(android.view.View view, User user);
    }
}
