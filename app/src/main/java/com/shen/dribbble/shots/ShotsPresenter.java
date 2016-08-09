package com.shen.dribbble.shots;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;

import java.util.List;

/**
 * Created by shen on 2016/8/2.
 */
public class ShotsPresenter implements ShotsContract.Presenter{

    private ShotsDataSource shotsDataSource;
    private ShotsContract.View shotsView;

    public ShotsPresenter(ShotsDataSource shotsDataSource,ShotsContract.View shotsView){
        this.shotsDataSource = shotsDataSource;
        this.shotsView = shotsView;
    }

    @Override
    public void loadShots(int page) {
        shotsDataSource.loadShots(page, new ShotsDataSource.LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<Shot> shots) {
                shotsView.showShots(shots);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void onShotItemClick(Shot shot, View view) {
        SimpleDraweeView draweeView = (SimpleDraweeView) ((ViewGroup)view).findViewById(R.id.shotDraw);
        shotsView.showShotDetail(shot,draweeView);
    }

    @Override
    public void onAvatarClick(User user, View view) {
        shotsView.showUserDetail(user,(SimpleDraweeView) view);
    }

}
