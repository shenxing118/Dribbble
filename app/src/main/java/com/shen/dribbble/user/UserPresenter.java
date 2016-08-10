package com.shen.dribbble.user;

import android.view.View;

import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.utils.BaseObserver;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/5.
 */
public class UserPresenter implements UserContract.Presenter {

    private ShotsDataSource shotsDataSource;
    private UserContract.View userView;

    public UserPresenter(ShotsDataSource shotsDataSource, UserContract.View userView) {
        this.shotsDataSource = shotsDataSource;
        this.userView = userView;
    }

    @Override
    public void getUserShots(String user, int page) {
        shotsDataSource.getUserShots(user, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<Shot>>() {
                        @Override
                        protected void next(List<Shot> shots) {
                            userView.showUserShots(shots);
                        }
                    });
    }

    @Override
    public void onItemClick(View view, Shot shot) {
        userView.showShotDetail(view, shot);
    }


}
