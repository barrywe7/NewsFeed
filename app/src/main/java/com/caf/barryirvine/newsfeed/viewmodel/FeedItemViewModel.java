package com.caf.barryirvine.newsfeed.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.web.Router;


/**
 * View Model for {@link FeedItem} Remember to use the {@link Bindable} annotation for all getters and to do
 * {@link #notifyPropertyChanged(int)} after a value is set in a setter.
 */

public class FeedItemViewModel extends BaseObservable {

    private final FeedItem mFeedItem;
    private final Router mRouter;

    public FeedItemViewModel(@NonNull final Context context, final FeedItem feedItem) {
        this(new Router(context), feedItem);
    }

    FeedItemViewModel(final Router router, final FeedItem feedItem) {
        mRouter = router;
        mFeedItem = feedItem;
    }

    @Bindable
    public String getTitle() {
        return mFeedItem.getTitle();
    }

    @Bindable
    public String getDescription() {
        return mFeedItem.getDescription();
    }

    @Bindable
    public String getImageUrl() {
        return mFeedItem.getImageUrl();
    }

    @Bindable
    public String getLink() {
        return mFeedItem.getLink();
    }

    public void onClick() {
        mRouter.startUrl(mFeedItem.getTitle(), mFeedItem.getLink());
    }
}
