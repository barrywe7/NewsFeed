package com.caf.barryirvine.newsfeed.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.api.RssService;
import com.caf.barryirvine.newsfeed.model.Feed;
import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.ui.adapter.FeedAdapter;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FeedFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
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
        getResults(false);
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
        if (savedInstanceState == null) {
            mAdapter = new FeedAdapter(Collections.<FeedItem>emptyList());
            mRecyclerView.setAdapter(mAdapter);
        }
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        if (((GridLayoutManager) mRecyclerView.getLayoutManager()).getSpanCount() > 1) {
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mEmptyView = view.findViewById(R.id.empty_state_layout);
    }

    @Override
    public void onRefresh() {
        getResults(true);
    }

    private void getResults(final boolean forceNetwork) {
        RssService.get(getContext(), forceNetwork)
                .getItems(mPathSegment)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Feed>() {
                            @Override
                            public void accept(final Feed feed) throws Exception {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mAdapter = new FeedAdapter(feed.getChannel().getFeedItems());
                                mRecyclerView.setAdapter(mAdapter);
                                mEmptyView.setVisibility(View.GONE);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(final Throwable throwable) throws Exception {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mEmptyView.setVisibility(View.VISIBLE);
                            }
                        });
    }

    private static class Args {
        private static final String FEED_TYPE_PATH_SEGMENT = "FEED_TYPE_PATH_SEGMENT";
    }
}
