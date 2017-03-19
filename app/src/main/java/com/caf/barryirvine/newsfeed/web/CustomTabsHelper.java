package com.caf.barryirvine.newsfeed.web;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.caf.barryirvine.newsfeed.R;
import com.caf.barryirvine.newsfeed.ui.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;

/**
 * Helper class for Custom Tabs.
 */
public class CustomTabsHelper {
    private static final String TAG = CustomTabsHelper.class.getSimpleName();
    private static final String STABLE_PACKAGE = "com.android.chrome";
    private static final String BETA_PACKAGE = "com.chrome.beta";
    private static final String DEV_PACKAGE = "com.chrome.dev";
    private static final String LOCAL_PACKAGE = "com.google.android.apps.chrome";

    private static String sPackageNameToUse;

    private CustomTabsHelper() {
    }

    public static void startUrl(@NonNull final Context context, final String title, final String url) {
        if (getPackageNameToUse(context.getPackageManager()) != null) {
            new CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setShowTitle(true)
                    .addDefaultShareMenuItem()
                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(context, android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right)
                    .build().launchUrl(context, Uri.parse(url));
        } else {
            WebViewActivity.start((Activity) context, title, url);
        }
    }


    /**
     * Goes through all apps that handle VIEW intents and have a warmup service. Picks
     * the one chosen by the user if there is one, otherwise makes a best effort to return a
     * valid package name.
     * <p>
     * This is <strong>not</strong> threadsafe.
     *
     * @param pm {@link PackageManager}.
     * @return The package name recommended to use for connecting to custom tabs related components.
     */
    private static String getPackageNameToUse(@NonNull final PackageManager pm) {
        if (sPackageNameToUse == null) {
            // Get default VIEW intent handler.
            final Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
            String defaultViewHandlerPackageName = null;
            if (pm.resolveActivity(activityIntent, 0) != null) {
                defaultViewHandlerPackageName = pm.resolveActivity(activityIntent, 0).activityInfo.packageName;
            }

            // Get all apps that can handle VIEW intents.
            final List<String> packagesSupportingCustomTabs = new ArrayList<>();
            for (final ResolveInfo info : pm.queryIntentActivities(activityIntent, 0)) {
                final Intent serviceIntent = new Intent(ACTION_CUSTOM_TABS_CONNECTION).setPackage(info.activityInfo.packageName);
                if (pm.resolveService(serviceIntent, 0) != null) {
                    packagesSupportingCustomTabs.add(info.activityInfo.packageName);
                }
            }

            // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
            // and service calls.
            if (packagesSupportingCustomTabs.isEmpty()) {
                sPackageNameToUse = null;
            } else if (packagesSupportingCustomTabs.size() == 1) {
                sPackageNameToUse = packagesSupportingCustomTabs.get(0);
            } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName) && !hasSpecializedHandlerIntents(pm, activityIntent)
                    && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
                sPackageNameToUse = defaultViewHandlerPackageName;
            } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
                sPackageNameToUse = STABLE_PACKAGE;
            } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
                sPackageNameToUse = BETA_PACKAGE;
            } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
                sPackageNameToUse = DEV_PACKAGE;
            } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
                sPackageNameToUse = LOCAL_PACKAGE;
            }
        }
        return sPackageNameToUse;
    }

    /**
     * Used to check whether there is a specialized handler for a given intent.
     *
     * @param intent The intent to check with.
     * @return Whether there is a specialized handler for the given intent.
     */
    private static boolean hasSpecializedHandlerIntents(@NonNull final PackageManager pm, @NonNull final Intent intent) {
        try {
            final List<ResolveInfo> handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
            if (handlers != null && handlers.size() > 0) {
                for (final ResolveInfo resolveInfo : handlers) {
                    final IntentFilter filter = resolveInfo.filter;
                    if (filter == null || (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) || resolveInfo.activityInfo == null) {
                        continue;
                    }
                    return true;
                }
            }
        } catch (final RuntimeException e) {
            Log.e(TAG, "Runtime exception while getting specialized handlers");
        }
        return false;
    }
}
