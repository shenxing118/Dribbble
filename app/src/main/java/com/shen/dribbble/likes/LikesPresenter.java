package com.shen.dribbble.likes;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.utils.BaseObserver;
import com.shen.dribbble.utils.UIUtils;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/7.
 */
public class LikesPresenter implements LikesContract.Presenter {

    LikesContract.View likesView;

    ShotsDataSource shotsDataSource;

    public LikesPresenter(LikesContract.View likesView,ShotsDataSource shotsDataSource) {
        this.likesView = likesView;
        this.shotsDataSource = shotsDataSource;
    }

    @Override
    public void loadLikes(int shotId, int page) {
        shotsDataSource.getShotLikes(shotId, page)
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
