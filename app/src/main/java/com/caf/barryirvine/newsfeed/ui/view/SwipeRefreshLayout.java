package com.caf.barryirvine.newsfeed.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.caf.barryirvine.newsfeed.R;

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private boolean mMeasured;
    private boolean mPreMeasuredRefresh;

    public SwipeRefreshLayout(final Context context) {
        super(context);
    }

    public SwipeRefreshLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mMeasured) {
            mMeasured = true;
            setRefreshing(mPreMeasuredRefresh);
        }
    }

    /**
     * Setting refreshing before the view has been measured doesn't work.
     *
     * @see <a href="http://stackoverflow.com/questions/30422471/swiperefreshlayout-inside-viewpager">bug</a>
     */
    @Override
    public void setRefreshing(boolean refreshing) {
        if (mMeasured) {
            super.setRefreshing(refreshing);
        } else {
            mPreMeasuredRefresh = refreshing;
        }
    }
}
