package com.caf.barryirvine.newsfeed.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.ui.recyclerview.ClickViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final ClickViewHolder.OnItemClickListener mClickListener;
    private final List<FeedItem> mItems;

    public FeedAdapter(final ClickViewHolder.OnItemClickListener clickListener, final List<FeedItem> items) {
        mClickListener = clickListener;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new FeedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FeedItem item = mItems.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .resizeDimen(R.dimen.thumbnail_size, R.dimen.thumbnail_size)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .centerCrop()
                .into(holder.mThumbnailImageView);
        holder.mTitleTextView.setText(item.getTitle());
        holder.mDescriptionTextView.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public FeedItem getItem(final int position) {
        return mItems.get(position);
    }


    class ViewHolder extends ClickViewHolder {
        private final ImageView mThumbnailImageView;
        private final TextView mTitleTextView;
        private final TextView mDescriptionTextView;


        ViewHolder(@NonNull final View itemView) {
            super(itemView, mClickListener);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.description_text_view);
        }
    }
}
