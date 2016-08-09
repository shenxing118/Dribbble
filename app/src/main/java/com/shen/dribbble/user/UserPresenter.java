package com.shen.dribbble.user;

import android.view.View;

import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.source.ShotsDataSource;

import java.util.List;

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
        shotsDataSource.getUserShots(user, page, new ShotsDataSource.LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<Shot> shots) {
                userView.showUserShots(shots);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void onItemClick(View view, Shot shot) {
        userView.showShotDetail(view, shot);
    }


}
