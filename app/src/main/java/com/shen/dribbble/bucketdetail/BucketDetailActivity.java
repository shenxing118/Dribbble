package com.shen.dribbble.bucketdetail;

import android.databinding.DataBindingUtil;
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
import com.shen.dribbble.INI;
import com.shen.dribbble.R;
import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.Shot;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.BucketHeaderBinding;
import com.shen.dribbble.shots.ShotsActivity;
import com.shen.dribbble.utils.CommonTools;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class BucketDetailActivity extends BaseActivity implements BucketDetailContract.View {

    private ShotsActivity.ShotsAdapter shotsAdapter;

    private SwipeRefreshLayout swipeRefreshL;

    private int page = 1;

    private BucketDetailContract.Presenter bucketsPresenter;

    private Bucket bucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shots_act);

        bucket = getIntent().getParcelableExtra("bucket");
        bucketsPresenter = new BucketDetailPresenter(ShotsRemoteDataSource.getInstance(), this);

        setToolBar("BucketDetail", true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shots_list);

        shotsAdapter = new ShotsActivity.ShotsAdapter(new ArrayList<Shot>(0), bucketsPresenter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (shotsAdapter.hasFooter() && position == shotsAdapter.getItemCount() - 1)
                        || (shotsAdapter.hasHeader() && position == 0) ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ShotsActivity.SpaceItemDecoration(CommonTools.dip2px(this, 10)));

        View headerView = LayoutInflater.from(this).inflate(R.layout.bucket_header,recyclerView,false);
        BucketHeaderBinding binding = DataBindingUtil.bind(headerView);
        binding.avatarImage.setImageURI(Uri.parse(bucket.getUser().getAvatarUrl()));
        binding.setVariable(BR.bucket,bucket);
        binding.executePendingBindings();

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer, recyclerView, false);

        shotsAdapter.setHeaderView(headerView);
        shotsAdapter.setFooterView(footerView);
        recyclerView.setAdapter(shotsAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && shotsAdapter.getItemCount() == lastVisibleItem + 1) {
                    getBucketShots();
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
                getBucketShots();
            }
        });

        getBucketShots();
    }

    private void getBucketShots() {
        bucketsPresenter.loadBucketShots(bucket.getId(), page);
    }

    @Override
    public void showBucketShots(List<Shot> shots) {
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


}
