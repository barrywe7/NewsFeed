package com.caf.barryirvine.newsfeed.viewmodel;

import com.caf.barryirvine.newsfeed.model.FeedItem;
import com.caf.barryirvine.newsfeed.model.MockModelUtils;
import com.caf.barryirvine.newsfeed.web.Router;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;


public class FeedItemViewModelTest {

    @Mock
    private Router mRouter;
    private FeedItem mFeedItem;
    private FeedItemViewModel mFeedItemViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mFeedItem = MockModelUtils.createMockFeedItem();
        mFeedItemViewModel = new FeedItemViewModel(mRouter, mFeedItem);
    }

    @Test
    public void shouldGetFeedItemTitle() throws Exception {
        assertEquals(mFeedItem.getTitle(), mFeedItemViewModel.getTitle());
    }

    @Test
    public void shouldGetFeedItemDescription() throws Exception {
        assertEquals(mFeedItem.getDescription(), mFeedItemViewModel.getDescription());
    }

    @Test
    public void shouldGetImageUrl() throws Exception {
        assertEquals(mFeedItem.getImageUrl(), mFeedItemViewModel.getImageUrl());
    }

    @Test
    public void shouldGetLink() throws Exception {
        assertEquals(mFeedItem.getLink(), mFeedItemViewModel.getLink());
    }

    @Test
    public void shouldOnClickWithCorrectTitleAndLink() throws Exception {
        mFeedItemViewModel.onClick();
        verify(mRouter).startUrl(mFeedItemViewModel.getTitle(), mFeedItemViewModel.getLink());
    }

}
