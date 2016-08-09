package com.shen.dribbble.shotdetail;

/**
 * Created by shen on 2016/8/7.
 */
public class ShotDetailPresenter implements ShotDetailContract.Presenter {

    private ShotDetailContract.View shotDetailView;

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
}
