package com.shen.dribbble.buckets;

import android.view.View;

import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.utils.BaseObserver;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/7.
 */
public class BucketsPresenter implements BucketsContract.Presenter {

    BucketsContract.View bucketsView;

    ShotsDataSource shotsDataSource;

    public BucketsPresenter(BucketsContract.View bucketsView, ShotsDataSource shotsDataSource) {
        this.bucketsView = bucketsView;
        this.shotsDataSource = shotsDataSource;
    }

    @Override
    public void loadBuckets(int shotId, int page) {
        shotsDataSource.getShotBuckets(shotId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Bucket>>(){
                    @Override
                    public void next(List<Bucket> buckets) {
                        bucketsView.showBuckets(buckets);
                    }
                });
    }

    @Override
    public void onAvatarClick(View view, User user) {
        bucketsView.showUser(view,user);
    }

    @Override
    public void onBucketItemClick(View view, Bucket bucket) {
        bucketsView.showBucketDetail(view,bucket);
    }
}