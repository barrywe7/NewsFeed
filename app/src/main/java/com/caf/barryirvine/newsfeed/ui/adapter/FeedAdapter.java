package com.caf.barryirvine.newsfeed.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.caf.barryirvine.newsfeed.BR;
import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.ui.recyclerview.ClickViewHolder;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final ClickViewHolder.OnItemClickListener mClickListener;
    private final List<FeedItem> mItems;

    public FeedAdapter(final ClickViewHolder.OnItemClickListener clickListener, final List<FeedItem> items) {
        mClickListener = clickListener;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, @LayoutRes final int viewType) {
        return new FeedAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_feed;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public FeedItem getItem(final int position) {
        return mItems.get(position);
    }

    class ViewHolder extends ClickViewHolder {

        private final ViewDataBinding mBinding;

        ViewHolder(@NonNull final ViewDataBinding binding) {
            super(binding.getRoot(), mClickListener);
            mBinding = binding;
        }

        void bind(@NonNull final FeedItem feedItem) {
            mBinding.setVariable(BR.feedItem, feedItem);
            mBinding.executePendingBindings();
        }
    }
}
