package me.micrusa.githubstats.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import org.tinylog.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestsUtil {

    public static void request(String url, final OnResponseListener ResponseListener){
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
