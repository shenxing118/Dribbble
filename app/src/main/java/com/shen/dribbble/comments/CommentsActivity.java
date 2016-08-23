package com.shen.dribbble.comments;

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
import com.shen.dribbble.data.Comment;
import com.shen.dribbble.data.User;
import com.shen.dribbble.data.source.ShotsRemoteDataSource;
import com.shen.dribbble.databinding.CommentItemBinding;
import com.shen.dribbble.utils.BaseRecyclerViewAdapter;
import com.shen.dribbble.utils.BaseViewHolder;
import com.shen.dribbble.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/8/6.
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View {

    private CommentsAdapter commentsAdapter;

    private int page = 1;

    private CommentsContract.Presenter commentsPresenter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshL;

    private int shotId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_act);

        setToolBar("Comments", true);

        shotId = getIntent().getIntExtra("shotId", 0);

        commentsPresenter = new CommentsPresenter(this, ShotsRemoteDataSource.getInstance());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsAdapter = new CommentsAdapter(new ArrayList<Comment>(0), commentsPresenter);

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_footer, recyclerView, false);
        commentsAdapter.setFooterView(footerView);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && commentsAdapter.getItemCount() == lastVisibleItem + 1) {
                    getComments();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        getComments();

        swipeRefreshL = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshL);
        swipeRefreshL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getComments();
            }
        });

    }

    private void getComments() {
        commentsPresenter.loadComments(shotId, page);
    }

    @Override
    public void showComments(List<Comment> comments) {
        if (comments.size() < INI.PAGE_SIZE) {
            commentsAdapter.removeFooter();
            recyclerView.setOnScrollListener(null);
        }
        if (page == 1) {
            commentsAdapter.setDatas(comments);
            swipeRefreshL.setRefreshing(false);
        } else {
            commentsAdapter.addDatas(comments);
        }
        page++;
    }

    @Override
    public void showUser(View view, User user) {
        UIUtils.openUserActivity(this, user, (SimpleDraweeView) view);
    }

    private static class CommentsAdapter extends BaseRecyclerViewAdapter<Comment> {

        public CommentsAdapter(List<Comment> mDatas, BasePresenter presenter) {
            super(mDatas, R.layout.comment_item, presenter);
        }

        @Override
        public void bindView(BaseViewHolder holder, int position) {
            final Comment comment = mDatas.get(position);
            final CommentItemBinding binding = (CommentItemBinding) holder.binding;
            binding.avatarImage.setImageURI(Uri.parse(comment.getUser().getAvatarUrl()));
            binding.commentContent.setText(comment.getBody().replaceAll("<p>|</p>", ""));
            binding.setVariable(BR.comment, comment);
            binding.setVariable(BR.presenter,presenter);
            binding.executePendingBindings();
        }
    }

}
