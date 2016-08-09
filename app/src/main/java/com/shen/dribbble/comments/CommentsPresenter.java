package com.shen.dribbble.comments;

import android.view.View;

import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;

import java.util.List;

/**
 * Created by shen on 2016/8/7.
 */
public class CommentsPresenter implements CommentsContract.Presenter {

    CommentsContract.View commentsView;

    ShotsDataSource shotsDataSource;

    public CommentsPresenter(CommentsContract.View commentsView, ShotsDataSource shotsDataSource) {
        this.commentsView = commentsView;
        this.shotsDataSource = shotsDataSource;
    }

    @Override
    public void loadComments(int shotId, int page) {
        shotsDataSource.getShotComments(shotId, page, new ShotsDataSource.LoadShotCommentsCallback() {

            @Override
            public void onCommentsLoad(List<Comment> comments) {
                commentsView.showComments(comments);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void onAvatarClick(View view, User user) {
        commentsView.showUser(view,user);
    }
}
