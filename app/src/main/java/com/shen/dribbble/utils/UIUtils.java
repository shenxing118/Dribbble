package com.shen.dribbble.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.shotdetail.ShotDetailActivity;
import com.shen.dribbble.user.UserActivity;

/**
 * Created by shen on 2016/8/8.
 */
public class UIUtils {

    public static void openShotDetailActivity(Activity activity, Shot shot, SimpleDraweeView draweeView) {
        Intent intent = new Intent(activity, ShotDetailActivity.class);
        intent.putExtra("shot", shot);
        Pair[] arrayOfPair = new Pair[1];
        arrayOfPair[0] = new Pair(draweeView, "shotImage");
        ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, arrayOfPair);
        ActivityCompat.startActivity(activity, intent, localActivityOptionsCompat.toBundle());
    }

    public static void openUserActivity(Activity activity, User user, SimpleDraweeView draweeView){
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra("user", user);
        Pair[] arrayOfPair = new Pair[1];
        arrayOfPair[0] = new Pair(draweeView, "avatarImage");
        ActivityOptionsCompat localActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, arrayOfPair);
        ActivityCompat.startActivity(activity, intent, localActivityOptionsCompat.toBundle());
    }

}