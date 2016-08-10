package com.shen.dribbble.user;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.BR;
import com.shen.dribbble.BaseActivity;
import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.UserHeaderBinding;
import com.shen.dribbble.databinding.UserShotItemBinding;
import com.shen.dribbble.utils.BaseRecyclerViewAdapter;
import com.shen.dribbble.utils.BaseViewHolder;
import com.shen.dribbble.utils.CommonTools;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shen on 2016/8/4.
 */
public class UserActivity extends BaseActivity implements UserContract.View {

    private User user;

    private ShotImageAdapter imageAdapter;

    private int page = 1;

    private static final int pageSize = 12;

    private UserContract.Presenter userPresenter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.user_act);

//        postponeEnterTransition();

        user = getIntent().getParcelableExtra("user");

        userPresenter = new UserPresenter(ShotsRemoteDataSource.getInstance(),this);

        setStatusBarColor();
        setToolBar(user.getName(),true);

        recyclerView = (RecyclerView) findViewById(R.id.profile_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (imageAdapter.hasHeader() && position == 0)
                        || (imageAdapter.hasFooter() && position == imageAdapter.getItemCount() - 1) ? 3 : 1;
            }
        });
        imageAdapter = new ShotImageAdapter(this, new ArrayList<Shot>(0),R.layout.user_shot_item,userPresenter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(CommonTools.dip2px(this, 2)));

        View headerView = LayoutInflater.from(this).inflate(R.layout.user_header, recyclerView, false);
        final UserHeaderBinding headerBinding = DataBindingUtil.bind(headerView);
        headerBinding.setUser(user);
        headerBinding.avatarImage.setImageURI(Uri.parse(user.getAvatarUrl()));

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer,recyclerView,false);

        imageAdapter.setHeaderView(headerView);
        imageAdapter.setFooterView(footerView);
        recyclerView.setAdapter(imageAdapter);

        loadDataAfterTransition(headerBinding);
//        startPostponedEnterTransition();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && imageAdapter.getItemCount() == lastVisibleItem + 1) {
                    getShotImage();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 头像共享元素动画结束后再加载数据，否则如果数据加载过快，将无法显示动画
     * @param headerBinding
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void loadDataAfterTransition(final UserHeaderBinding headerBinding) {
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                getShotImage();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put("avatarImage", headerBinding.avatarImage);
                super.onMapSharedElements(names, sharedElements);
            }
        });
    }

    private void getShotImage() {
        userPresenter.getUserShots(user.getUsername(),page);
    }

    @Override
    public void showUserShots(List<Shot> shots) {
        if (shots.size() < pageSize){
            imageAdapter.removeFooter();
            recyclerView.setOnScrollListener(null);
        }
        if (page == 1) {
            imageAdapter.setDatas(shots);
        } else {
            imageAdapter.addDatas(shots);
        }
        page++;
    }

    @Override
    public void showShotDetail(View view, Shot shot) {
        shot.setUser(user);
        UIUtils.openShotDetailActivity(this,shot, (SimpleDraweeView) view);
    }

    private static class ShotImageAdapter extends BaseRecyclerViewAdapter<Shot>{

        public ShotImageAdapter(Context context, List<Shot> mDatas, int resourceId, BasePresenter presenter) {
            super(context, mDatas, resourceId, presenter);
        }

        @Override
        public void bindView(BaseViewHolder holder, int position) {
            final Shot shot = mDatas.get(position - 1);
            UserShotItemBinding binding = (UserShotItemBinding) holder.binding;
            binding.shotDraw.setImageURI(Uri.parse(shot.getImages().getNormal()));
            binding.setVariable(BR.shot,shot);
            binding.setVariable(BR.presenter,presenter);
            binding.executePendingBindings();
        }
    }

    private static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) % 3 != 1) {
                outRect.left = space;
            }
            outRect.bottom = space;
        }
    }


}
