package com.shen.dribbble.data.source;

import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.Shot;

import java.util.List;

/**
 * Created by shen on 2016/8/2.
 */
public interface ShotsDataSource {

    interface LoadShotsCallback{
        void onShotsLoaded(List<Shot> shots);
        void onDataNotAvailable();
    }

    interface LoadShotLikesCallback{
        void onLikesLoad(List<Like> likes);
        void onDataNotAvailable();
    }

    interface LoadShotCommentsCallback{
        void onCommentsLoad(List<Comment> comments);
        void onDataNotAvailable();
    }


    void loadShots(int page,LoadShotsCallback callback);

    void getUserShots(String user,int page,LoadShotsCallback callback);

    void getShotLikes(int shotId,int page,LoadShotLikesCallback callback);

    void getShotComments(int shotId,int page,LoadShotCommentsCallback callback);

}
