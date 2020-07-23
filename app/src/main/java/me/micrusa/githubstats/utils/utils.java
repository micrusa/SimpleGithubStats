package me.micrusa.githubstats.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.pixplicity.easyprefs.library.Prefs;

import me.micrusa.githubstats.Constants;

public class utils {

    public static boolean isNull(String string){
        return string == null || string.equals("");
    }

    public static int getCacheTime(){
        return Prefs.getInt(Constants.KEY_CACHETIME, 3600) * 1000; //To millis
    }

    public static void openLink(String url, Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
