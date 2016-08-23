package com.shen.dribbble.shotdetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.shen.dribbble.BaseActivity;
import com.shen.dribbble.R;
import com.shen.dribbble.buckets.BucketsActivity;
import com.shen.dribbble.comments.CommentsActivity;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.databinding.ShotDetailActBinding;
import com.shen.dribbble.likes.LikesActivity;
import com.shen.dribbble.utils.CommonTools;
import com.shen.dribbble.utils.UIUtils;

/**
 * Created by shen on 2016/7/27.
 */
public class ShotDetailActivity extends BaseActivity implements ShotDetailContract.View {

    private Shot shot;
    private SimpleDraweeView shotDraw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.setSetStatusBarColor(false);
        super.onCreate(savedInstanceState);

        ShotDetailActBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.shot_detail_act);

        ShotDetailPresenter shotDetailPresenter = new ShotDetailPresenter(this);

        shot = getIntent().getParcelableExtra("shot");

        dataBinding.setShot(shot);
        dataBinding.setPresenter(shotDetailPresenter);
        dataBinding.executePendingBindings();

        shotDraw = dataBinding.shotDraw;
        AppBarLayout appbar = dataBinding.appbar;

        setToolBar(shot.getTitle(), true);

        int screenWidth = CommonTools.getScreenWidth();
        appbar.getLayoutParams().width = screenWidth;
        appbar.getLayoutParams().height = (int) (screenWidth * 3.0 / 4);

        dataBinding.avatarImage.setImageURI(Uri.parse(shot.getUser().getAvatarUrl()));
        loadShotImage();

        String description = shot.getDescription();
        if (!TextUtils.isEmpty(description)) {

//            Spanned localSpanned = GoURLSpan.a(Html.fromHtml(description));
            dataBinding.description.setText(Html.fromHtml(description));
            dataBinding.description.setMovementMethod(LinkMovementMethod.getInstance());
//            dataBinding.description.requestLayout();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadShotImage() {
        ProgressBarDrawable progressBarDrawable = new ProgressBarDrawable();
        progressBarDrawable.setBarWidth(5);
        progressBarDrawable.setPadding(0);
        progressBarDrawable.setColor(getResources().getColor(R.color.colorPrimary));

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources()).setProgressBarImage(progressBarDrawable).setPlaceholderImage(R.mipmap.placeholder).setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(shot.getImages().getNormal()))
                .setImageRequest(ImageRequest.fromUri(shot.getImages().getHidpi()))
                .setAutoPlayAnimations(true)
                .setOldController(shotDraw.getController())
                .build();
        shotDraw.setHierarchy(hierarchy);
        shotDraw.setController(controller);
    }

    @Override
    public void showLikes(int shotId) {
        Intent intent = new Intent(this, LikesActivity.class);
        intent.putExtra("shotId", shotId);
        startActivity(intent);
    }

    @Override
    public void showComments(int shotId) {
        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra("shotId", shotId);
        startActivity(intent);
    }

    @Override
    public void showBuckets(int shotId) {
        Intent intent = new Intent(this, BucketsActivity.class);
        intent.putExtra("shotId", shotId);
        startActivity(intent);
    }

    @Override
    public void showUserUI(User user,View view) {
        UIUtils.openUserActivity(this,user,(SimpleDraweeView) view);
    }

}
