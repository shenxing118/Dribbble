package com.shen.dribbble.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shen.dribbble.BasePresenter;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas;
    private final int resourceId;
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_FOOTER = 2;

    private View headerView;
    private View footerView;

    protected final BasePresenter presenter;

    public BaseRecyclerViewAdapter(List<T> mDatas, int resourceId, BasePresenter presenter) {
        this.mDatas = mDatas;
        this.resourceId = resourceId;
        this.presenter = presenter;
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas) {
        int startPosition = getItemCount();
        this.mDatas.addAll(datas);
        notifyItemRangeInserted(startPosition, datas.size());
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void removeFooter() {
        setFooterView(null);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) return new HeaderOrFooterViewHolder(DataBindingUtil.bind(headerView));
        if (viewType == ITEM_TYPE_FOOTER) return new HeaderOrFooterViewHolder(DataBindingUtil.bind(footerView));
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), resourceId, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHeader(position) || isFooter(position)) {
            return;
        }
        bindView(holder,position);
    }

    public abstract void bindView(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mDatas == null) return 0;
        int count = mDatas.size();
        if (hasHeader()) {
            count++;
        }
        if (hasFooter()) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return ITEM_TYPE_HEADER;
        } else if (isFooter(position)) {
            return ITEM_TYPE_FOOTER;
        }
        return ITEM_TYPE_NORMAL;
    }

    public boolean hasHeader() {
        return headerView != null;
    }

    public boolean hasFooter() {
        return footerView != null;
    }

    private boolean isHeader(int position) {
        return position == 0 && hasHeader();
    }

    private boolean isFooter(int position) {
        return position == getItemCount() - 1 && footerView != null;
    }

}