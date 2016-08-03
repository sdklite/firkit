package com.sdklite.firkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.sdklite.firkit.util.TypeResolver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public abstract class FirKit {

    private static final String FIR_API_TOKEN = "im.fir.API_TOKEN";

    private static final String API_APP_LIST = "http://api.fir.im/apps";

    private static final String API_APP_LATEST = "http://api.fir.im/apps/latest/";

    public static final FirKit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static final FirKit INSTANCE = new FirKit() {
        };
    }

    private FirKit() {
    }

    public final ApplicationManager getApplicationManager(final Context context) {
        final Context app = context.getApplicationContext();
        final String pkg = app.getPackageName();
        final String token = getFirApiToken(app);

        return new ApplicationManager() {
            @Override
            public void latest(final Callback<Version> callback) {
                final String url = new StringBuilder(API_APP_LATEST).append(pkg).append("?type=android&api_token=").append(token).toString();
                fetch(url, callback);
            }

            @Override
            public void list(final Callback<List<Application>> callback) {
                final String url = new StringBuilder(API_APP_LIST).append("?api_token=").append(token).toString();
                fetch(url, callback);
            }

            @Override
            public void get(final String id, final Callback<Application> callback) {
                final String url = new StringBuilder(API_APP_LIST).append("?api_token=").append(token).toString();
                fetch(url, callback);
            }

            @Override
            public void install(final String url) {
                // download from the specified url
                final DownloadManager dm = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
                final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
                app.registerReceiver(new BroadcastReceiver() {
                    private final long downloadId = dm.enqueue(request);

                    @Override
                    public void onReceive(final Context context, final Intent intent) {
                        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                            final long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                            if (this.downloadId == downloadId) {
                                // unregister broadcast receiver
                                app.unregisterReceiver(this);

                                // install package
                                final Uri uri = dm.getUriForDownloadedFile(downloadId);
                                final Intent installIntent = new Intent(Intent.ACTION_VIEW);
                                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                app.startActivity(intent);
                            }
                        }
                    }
                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        };
    }

    public static <T> void fetch(final String url, final Callback<T> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream input = null;

                try {
                    input = new URL(url).openStream();
                    final T t = new Gson().fromJson(new InputStreamReader(input), TypeResolver.getParameterizedGenericType(callback, Callback.class));
                    callback.onSuccess(t);
                } catch (final Exception e) {
                    callback.onFailure(e);
                } finally {
                    if (null != input) {
                        try {
                            input.close();
                        } catch (final IOException e) {
                        }
                    }
                }
            }
        }).start();
    }

    private static String getFirApiToken(final Context context) {
        try {
            final ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            if (null == appInfo.metaData || !appInfo.metaData.containsKey(FIR_API_TOKEN)) {
                throw new RuntimeException("FIR_API_TOKEN not found in metadata");
            }

            return appInfo.metaData.getString(FIR_API_TOKEN);
        } catch (final NameNotFoundException e) {
            throw new RuntimeException("FIR_API_TOKEN not found in metadata", e);
        }
    }
}
