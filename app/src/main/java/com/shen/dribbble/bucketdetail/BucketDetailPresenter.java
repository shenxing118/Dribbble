package com.shen.dribbble.bucketdetail;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Bucket;
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
public class BucketDetailPresenter implements BucketDetailContract.Presenter{

    private final ShotsDataSource shotsDataSource;
    private final BucketDetailContract.View shotsView;

    public BucketDetailPresenter(ShotsDataSource shotsDataSource, BucketDetailContract.View shotsView){
        this.shotsDataSource = shotsDataSource;
        this.shotsView = shotsView;
    }

    @Override
    public void loadBucketShots(int bucketId,int page) {
        shotsDataSource.getBucketShots(bucketId,page)
                    .compose(((RxAppCompatActivity)shotsView).<List<Shot>>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<Shot>>() {
                        @Override
                        protected void next(List<Shot> shots) {
                            shotsView.showBucketShots(shots);
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
