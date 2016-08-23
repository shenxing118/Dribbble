package com.shen.dribbble.shots;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.utils.BaseObserver;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/2.
 */
public class ShotsPresenter implements ShotsContract.Presenter{

    private final ShotsDataSource shotsDataSource;
    private final ShotsContract.View shotsView;

    public ShotsPresenter(ShotsDataSource shotsDataSource,ShotsContract.View shotsView){
        this.shotsDataSource = shotsDataSource;
        this.shotsView = shotsView;
    }

    @Override
    public void loadShots(int page) {
        shotsDataSource.loadShots(page)
                .compose(((RxAppCompatActivity)shotsView).<List<Shot>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<Shot>>() {
                        @Override
                        protected void next(List<Shot> shots) {
                            shotsView.showShots(shots);
                        }
                    });
    }

    @Override
    public void onShotItemClick(Shot shot, View view) {
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.shotDraw);
        shotsView.showShotDetail(shot,draweeView);
    }

    @Override
    public void onAvatarClick(User user, View view) {
        shotsView.showUserDetail(user,(SimpleDraweeView) view);
    }

}
