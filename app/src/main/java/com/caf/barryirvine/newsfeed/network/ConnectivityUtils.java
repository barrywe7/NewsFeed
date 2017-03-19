package com.caf.barryirvine.newsfeed.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectivityUtils {

    static boolean isConnected(final Context context) {
        final NetworkInfo activeNetwork = (
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }
}
