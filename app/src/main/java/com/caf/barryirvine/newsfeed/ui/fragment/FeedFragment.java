package com.caf.barryirvine.newsfeed.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.api.RssAPIClient;
import com.caf.barryirvine.newsfeed.model.Feed;
import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.ui.adapter.FeedAdapter;
import com.caf.barryirvine.newsfeed.ui.recyclerview.ClickViewHolder;
import com.caf.barryirvine.newsfeed.web.CustomTabsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment implements ClickViewHolder.OnItemClickListener, Callback<Feed>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedAdapter mAdapter;
    private String mPathSegment;

    public FeedFragment() {
    }

    public static FeedFragment newInstance(@NonNull final String pathSegment) {
        final FeedFragment fragment = new FeedFragment();
        final Bundle args = new Bundle();
        args.putString(Args.FEED_TYPE_PATH_SEGMENT, pathSegment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPathSegment = getArguments().getString(Args.FEED_TYPE_PATH_SEGMENT);
        RssAPIClient.get(getContext(), false)
                .getItems(mPathSegment)
                .enqueue(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (savedInstanceState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(Args.RECYCLER_VIEW_STATE));
            //TODO: Restore values of recycler view and set to adapter
        }
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (((GridLayoutManager) mRecyclerView.getLayoutManager()).getSpanCount() > 1) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Args.RECYCLER_VIEW_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onItemClick(@NonNull final View view, final int position) {
        final FeedItem item = mAdapter.getItem(position);
        CustomTabsHelper.startUrl(getContext(), item.getTitle(), item.getLink());
    }

    @Override
    public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (response.isSuccessful()) {
            mAdapter = new FeedAdapter(this, response.body().getChannel().getFeedItems());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onFailure(final Call<Feed> call, final Throwable t) {
        Log.e(FeedFragment.class.getSimpleName(), "Development error retrieving feed", t);
    }

    @Override
    public void onRefresh() {
        RssAPIClient.get(getContext(), true)
                .getItems(mPathSegment)
                .enqueue(this);
    }

    private static class Args {
        private static final String FEED_TYPE_PATH_SEGMENT = "FEED_TYPE_PATH_SEGMENT";
        private static final String RECYCLER_VIEW_STATE = "RECYCLER_VIEW_STATE";
    }
}
