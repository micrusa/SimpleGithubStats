package me.micrusa.githubstats.utils;

import android.content.Context;
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
        request(url, ResponseListener, true);
    }

    public static void request(String url, final OnResponseListener ResponseListener, boolean useCachedData){
        Realm realm = Realm.getDefaultInstance();
        CachedRequest cachedRequest = realm.where(CachedRequest.class).equalTo("url", url).findFirst();
        if (useCachedData && isCacheValid(cachedRequest)) {
            Logger.debug("Using cached data");
            ResponseListener.onResponse(true, cachedRequest.getCachedResponse());
        } else {
            makeRequest(url, ResponseListener, cachedRequest);
        }
    }

    private static void makeRequest(String url, OnResponseListener listener, CachedRequest cachedRequest){
        prepareLooper();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
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
                    newRealm.close();
                }

                checkForErrors(data);
                handler.post(() -> listener.onResponse(success, data));
            } catch (IOException e) {
                Logger.error(e);
            }
        }).start();
    }

    private static void checkForErrors(String data){
        Context context = MainApplication.getApp().getApplicationContext();
        switch(data){
            case "403": //Rate-limited
                Toast.makeText(context, R.string.ratelimited, Toast.LENGTH_SHORT).show();
                break;
            case "500":
            case "502":
            case "503":
                Toast.makeText(context, context.getString(R.string.servererror, data), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private static boolean isCacheValid(CachedRequest cachedRequest){
        return cachedRequest != null && cachedRequest.getCachedResponse() != null && System.currentTimeMillis() - cachedRequest.getLatestCache() <= Utils.getCacheTime();
    }

    private static void prepareLooper(){
        if(Looper.myLooper() == null) Looper.prepare();
    }

    public interface OnResponseListener{
        void onResponse(boolean isSuccess, String response);
    }

}
