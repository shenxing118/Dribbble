package com.shen.dribbble.utils;

import rx.Observer;

/**
 * Created by shen on 2016/8/10.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        next(t);
    }

    protected abstract void next(T t);
}
