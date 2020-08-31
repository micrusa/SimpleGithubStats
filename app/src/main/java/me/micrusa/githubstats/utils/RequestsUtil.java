package me.micrusa.githubstats.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import io.realm.Realm;
import io.realm.RealmResults;
import me.micrusa.githubstats.MainApplication;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.CachedRequest;
import me.micrusa.githubstats.objects.realm.Repo;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestsUtil {

    public static void request(String url, final OnResponseListener ResponseListener){

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CachedRequest> cache = realm.where(CachedRequest.class).equalTo("url", url).findAll();
        CachedRequest cachedRequest = null;
        if(cache.size() >= 1){
            cachedRequest = cache.get(0);
            if(cachedRequest.getCachedResponse() != null && System.currentTimeMillis() - cachedRequest.getLatestCache() <= utils.getCacheTime()){
                Logger.debug("Using cached data");
                ResponseListener.onResponse(true, cachedRequest.getCachedResponse());
                return;
            }
        }
        CachedRequest finalCachedRequest = cachedRequest;

        if(Looper.myLooper() == null) Looper.prepare();
        Handler handler = new Handler(Looper.getMainLooper());
        Thread requestThread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                boolean success = response.isSuccessful();
                String data = success ? response.body().string() : String.valueOf(response.code());
                if(success){
                    Realm newRealm = Realm.getDefaultInstance();
                    newRealm.beginTransaction();
                    CachedRequest newCache = finalCachedRequest == null ? newRealm.createObject(CachedRequest.class, url) : newRealm.where(CachedRequest.class).equalTo("url", url).findFirst();
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
