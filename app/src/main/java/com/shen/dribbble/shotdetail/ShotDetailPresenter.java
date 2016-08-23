package com.shen.dribbble.shotdetail;

import android.view.View;

import com.shen.dribbble.data.User;

/**
 * Created by shen on 2016/8/7.
 */
public class ShotDetailPresenter implements ShotDetailContract.Presenter {

    private final ShotDetailContract.View shotDetailView;

    public ShotDetailPresenter(ShotDetailContract.View shotDetailView){
        this.shotDetailView = shotDetailView;
    }

    @Override
    public void onLikesClick(int shotId) {
        shotDetailView.showLikes(shotId);
    }

    @Override
    public void onCommentsClick(int shotId) {
        shotDetailView.showComments(shotId);
    }

    @Override
    public void onBucketsClick(int shotId) {
        shotDetailView.showBuckets(shotId);
    }

    @Override
    public void onAvatarClick(User user, View view) {
        shotDetailView.showUserUI(user,view);
    }
}
