package com.caf.barryirvine.newsfeed.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ClickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final OnItemClickListener mOnItemClickListener;

    public ClickViewHolder(@NonNull final View itemView, @Nullable final OnItemClickListener onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;
        if (onItemClickListener != null) {
            itemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(@NonNull final View view) {
        final int itemPosition = getAdapterPosition();
        if (itemPosition != RecyclerView.NO_POSITION) {
            mOnItemClickListener.onItemClick(view, itemPosition);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull final View view, final int position);
    }
}
