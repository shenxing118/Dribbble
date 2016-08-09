package com.shen.dribbble.likes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
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
import com.shen.dribbble.data.Like;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsDataSource;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.LikeItemBinding;
import com.shen.dribbble.user.UserActivity;
import com.shen.dribbble.utils.BaseRecyclerViewAdapter;
import com.shen.dribbble.utils.BaseViewHolder;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/8/6.
 */
public class LikesActivity extends BaseActivity implements LikesContract.View{

    LikeAdapter likeAdapter;

    int page = 1;

    LikesContract.Presenter likesPresenter;

    RecyclerView recyclerView;

    private int shotId;
    private SwipeRefreshLayout swipeRefreshL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.likes_act);

        setStatusBarColor();
        setToolBar("Likes",true);

        shotId = getIntent().getIntExtra("shotId",0);

        recyclerView = (RecyclerView) findViewById(R.id.likes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        likeAdapter = new LikeAdapter(this,new ArrayList<Like>(0),R.layout.like_item,likesPresenter);

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer,recyclerView,false);
        likeAdapter.setFooterView(footerView);
        recyclerView.setAdapter(likeAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && likeAdapter.getItemCount() == lastVisibleItem + 1) {
                    getLikes();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        likesPresenter = new LikesPresenter(this,ShotsRemoteDataSource.getInstance());
        getLikes();

        swipeRefreshL = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshL);
        swipeRefreshL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getLikes();
            }
        });
    }

    private void getLikes(){
        likesPresenter.loadLikes(shotId,page);
    }

    @Override
    public void showLikes(List<Like> likes) {
        if (likes.size() < INI.PAGE_SIZE){
            likeAdapter.removeFooter();
            recyclerView.setOnScrollListener(null);
        }
        if (page == 1){
            likeAdapter.setDatas(likes);
            swipeRefreshL.setRefreshing(false);
        }else{
            likeAdapter.addDatas(likes);
        }
        page++;
    }

    @Override
    public void showUser(View view, User user) {
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.avatarImage);
        UIUtils.openUserActivity(this,user,draweeView);
    }

    private static class LikeAdapter extends BaseRecyclerViewAdapter<Like>{

        public LikeAdapter(Context context, List<Like> mDatas, int resourceId, BasePresenter presenter) {
            super(context, mDatas, resourceId, presenter);
        }

        @Override
        public void bindView(BaseViewHolder holder, int position) {
            final Like like = mDatas.get(position);
            final LikeItemBinding binding= (LikeItemBinding) holder.binding;
            binding.avatarImage.setImageURI(Uri.parse(like.getUser().getAvatarUrl()));
            binding.setVariable(BR.like,like);
            binding.setVariable(BR.presenter,presenter);
            binding.executePendingBindings();
        }
    }
}
