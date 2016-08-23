package com.shen.dribbble.shots;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.BR;
import com.shen.dribbble.BaseActivity;
import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.INI;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.ShotItemBinding;
import com.shen.dribbble.utils.BaseRecyclerViewAdapter;
import com.shen.dribbble.utils.BaseViewHolder;
import com.shen.dribbble.utils.CommonTools;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class ShotsActivity extends BaseActivity implements ShotsContract.View {

    private ShotsAdapter shotsAdapter;

    private SwipeRefreshLayout swipeRefreshL;

    private int page = 1;

    private ShotsContract.Presenter shotsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shots_act);

        shotsPresenter = new ShotsPresenter(ShotsRemoteDataSource.getInstance(), this);

        setToolBar("Dribbble", false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shots_list);

        shotsAdapter = new ShotsAdapter(new ArrayList<Shot>(0), shotsPresenter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return shotsAdapter.hasFooter() && position == shotsAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(CommonTools.dip2px(this, 10)));
        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer, recyclerView, false);
        shotsAdapter.setFooterView(footerView);
        recyclerView.setAdapter(shotsAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && shotsAdapter.getItemCount() == lastVisibleItem + 1) {
                    getShots();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        swipeRefreshL = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshL);
        swipeRefreshL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getShots();
            }
        });

        getShots();


    }

    private void getShots() {
        shotsPresenter.loadShots(page);
    }

    @Override
    public void showShots(List<Shot> shots) {
        if (shots.size() < INI.PAGE_SIZE){
            shotsAdapter.removeFooter();
        }
        if (page == 1) {
            shotsAdapter.addDatas(shots);
            swipeRefreshL.setRefreshing(false);
        } else {
            shotsAdapter.addDatas(shots);
        }
        page++;
    }

    @Override
    public void showShotDetail(Shot shot, SimpleDraweeView draweeView) {
        UIUtils.openShotDetailActivity(this, shot, draweeView);
    }

    @Override
    public void showUserDetail(User user, SimpleDraweeView draweeView) {
        UIUtils.openUserActivity(this, user, draweeView);
    }

    public static class ShotsAdapter extends BaseRecyclerViewAdapter<Shot> {


        public ShotsAdapter(List<Shot> mDatas, BasePresenter presenter) {
            super(mDatas, R.layout.shot_item, presenter);
        }

        @Override
        public void bindView(BaseViewHolder holder, int position) {
            final Shot shot = hasHeader() ? mDatas.get(position - 1) : mDatas.get(position);
            final ShotItemBinding binding = (ShotItemBinding) holder.binding;
            binding.shotDraw.setImageURI(Uri.parse(shot.getImages().getNormal()));
            binding.avatarImage.setImageURI(Uri.parse(shot.getUser().getAvatarUrl()));
            binding.setVariable(BR.shot, shot);
            binding.setVariable(BR.presenter, presenter);
            binding.executePendingBindings();
        }
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            outRect.set(space, 0, 0, space);

        }
    }


}
