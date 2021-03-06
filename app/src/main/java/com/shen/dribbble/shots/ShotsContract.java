package com.shen.dribbble.shots;

import com.shen.dribbble.data.Shot;

import java.util.List;

/**
 * Created by shen on 2016/8/2.
 */
public interface ShotsContract {

    interface Presenter extends ShotItemContract.Presenter {
        void loadShots(int page);
    }

    interface View extends ShotItemContract.View {
        void showShots(List<Shot> shots);
    }


}
