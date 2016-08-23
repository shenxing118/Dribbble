package com.shen.dribbble.likes;

import android.app.Activity;
import android.view.View;

import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.utils.BaseObserver;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/7.
 */
public class LikesPresenter implements LikesContract.Presenter {

    private final LikesContract.View likesView;

    private final ShotsDataSource shotsDataSource;

    public LikesPresenter(LikesContract.View likesView,ShotsDataSource shotsDataSource) {
        this.likesView = likesView;
        this.shotsDataSource = shotsDataSource;
    }

    @Override
    public void loadLikes(int shotId, int page) {
        shotsDataSource.getShotLikes(shotId, page)
                .compose(((RxAppCompatActivity)likesView).<List<Like>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Like>>(){
                    @Override
                    protected void next(List<Like> likes) {
                        likesView.showLikes(likes);
                    }
                });
    }

    @Override
    public void onItemClick(View view, User user) {
        likesView.showUser(view,user);
    }
}
