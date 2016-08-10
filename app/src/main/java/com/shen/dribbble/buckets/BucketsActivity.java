package com.shen.dribbble.buckets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shen.dribbble.BR;
import com.shen.dribbble.BaseActivity;
import com.shen.dribbble.BasePresenter;
import com.shen.dribbble.INI;
import com.shen.dribbble.R;
import com.shen.dribbble.bucketdetail.BucketDetailActivity;
import com.shen.dribbble.data.Bucket;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.BucketItemBinding;
import com.shen.dribbble.utils.BaseRecyclerViewAdapter;
import com.shen.dribbble.utils.BaseViewHolder;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/8/6.
 */
public class BucketsActivity extends BaseActivity implements BucketsContract.View {

    private BucketsAdapter bucketsAdapter;

    private int page = 1;

    private BucketsContract.Presenter bucketsPresenter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshL;

    private int shotId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_act);

        setStatusBarColor();
        setToolBar("Buckets", true);

        shotId = getIntent().getIntExtra("shotId", 0);
        bucketsPresenter = new BucketsPresenter(this, ShotsRemoteDataSource.getInstance());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bucketsAdapter = new BucketsAdapter(this, new ArrayList<Bucket>(0), R.layout.bucket_item, bucketsPresenter);

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer, recyclerView, false);
        bucketsAdapter.setFooterView(footerView);
        recyclerView.setAdapter(bucketsAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && bucketsAdapter.getItemCount() == lastVisibleItem + 1) {
                    getBuckets();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        getBuckets();

        swipeRefreshL = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshL);
        swipeRefreshL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getBuckets();
            }
        });

    }

    private void getBuckets() {
        bucketsPresenter.loadBuckets(shotId, page);
    }

    @Override
    public void showBuckets(List<Bucket> buckets) {
        if (buckets.size() < INI.PAGE_SIZE) {
            bucketsAdapter.removeFooter();
            recyclerView.setOnScrollListener(null);
        }
        if (page == 1) {
            bucketsAdapter.setDatas(buckets);
            swipeRefreshL.setRefreshing(false);
        } else {
            bucketsAdapter.addDatas(buckets);
        }
        page++;
    }

    @Override
    public void showUser(View view, User user) {
        UIUtils.openUserActivity(this, user, (SimpleDraweeView) view);
    }

    @Override
    public void showBucketDetail(View view, Bucket bucket) {
        Intent intent = new Intent(this, BucketDetailActivity.class);
        intent.putExtra("bucket",bucket);
        startActivity(intent);
    }

    private static class BucketsAdapter extends BaseRecyclerViewAdapter<Bucket> {

        public BucketsAdapter(Context context, List<Bucket> mDatas, int resourceId, BasePresenter presenter) {
            super(context, mDatas, resourceId, presenter);
        }

        @Override
        public void bindView(BaseViewHolder holder, int position) {
            final Bucket bucket = mDatas.get(position);
            final BucketItemBinding binding = (BucketItemBinding) holder.binding;
            binding.avatarImage.setImageURI(Uri.parse(bucket.getUser().getAvatarUrl()));
            binding.setVariable(BR.bucket, bucket);
            binding.setVariable(BR.presenter,presenter);
            binding.executePendingBindings();
        }
    }

}
