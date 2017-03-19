package com.caf.barryirvine.newsfeed.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.ui.adapter.FeedPagerAdapter;

public class FeedPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private FeedPagerAdapter mAdapter;

    public FeedPagerFragment() {
    }

    public static FeedPagerFragment newInstance() {
        final FeedPagerFragment fragment = new FeedPagerFragment();
        fragment.setArguments(Bundle.EMPTY);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_pager, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mAdapter = new FeedPagerAdapter(getContext(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
