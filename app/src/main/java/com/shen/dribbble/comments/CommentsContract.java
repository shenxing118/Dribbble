package com.shen.dribbble.comments;

import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.BaseView;
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;

import java.util.List;

/**
 * Created by shen on 2016/8/7.
 */
public interface CommentsContract {

    interface Presenter extends BasePresenter{
        void loadComments(int shotId, int page);

        void onAvatarClick(android.view.View view , User user);
    }

    interface View extends BaseView<Presenter>{
        void showComments(List<Comment> comments);

        void showUser(android.view.View view,User user);
    }
}
