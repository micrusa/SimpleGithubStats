package me.micrusa.githubstats.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.tinylog.Logger;

import java.io.IOException;

import io.realm.Realm;
import me.micrusa.githubstats.MainApplication;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.CachedRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestsUtil {

    public static void request(String url, final OnResponseListener ResponseListener){

        Realm realm = Realm.getDefaultInstance();
        CachedRequest cachedRequest = realm.where(CachedRequest.class).equalTo("url", url).findFirst();
        if (cachedRequest.getCachedResponse() != null && System.currentTimeMillis() - cachedRequest.getLatestCache() <= utils.getCacheTime()) {
            Logger.debug("Using cached data");
            ResponseListener.onResponse(true, cachedRequest.getCachedResponse());
            return;
        }

        if(Looper.myLooper() == null) Looper.prepare();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread requestThread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url).build();

            try (Response response = client.newCall(request).execute()) {
                boolean success = response.isSuccessful();
                String data = success ? response.body().string() : String.valueOf(response.code());
                if(success){
                    Realm newRealm = Realm.getDefaultInstance();
                    newRealm.beginTransaction();
                    CachedRequest newCache = cachedRequest == null ? newRealm.createObject(CachedRequest.class, url) : newRealm.where(CachedRequest.class).equalTo("url", url).findFirst();
                    newCache.setCachedResponse(data);
                    newCache.setLatestCache(System.currentTimeMillis());
                    newRealm.commitTransaction();
                }

                if(Looper.myLooper() == null) Looper.prepare();
                if(data.equals("403")) //Rate-limited
                    Toast.makeText(MainApplication.getApp().getApplicationContext(), R.string.ratelimited, Toast.LENGTH_SHORT).show();
                handler.post(() -> ResponseListener.onResponse(success, data));
            } catch (IOException e) {
                Logger.error(e);
            }
        });

        requestThread.start();
    }

    public interface OnResponseListener{
        void onResponse(boolean isSuccess, String response);
    }

}
