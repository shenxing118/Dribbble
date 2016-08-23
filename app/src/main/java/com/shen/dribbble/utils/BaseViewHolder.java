package com.shen.dribbble.utils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shen on 2016/8/7.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public final ViewDataBinding binding;
    private View.OnClickListener onClickListener;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(itemView);
                }
            }
        });
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
