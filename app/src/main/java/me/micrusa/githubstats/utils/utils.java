package me.micrusa.githubstats.utils;

import com.pixplicity.easyprefs.library.Prefs;

import me.micrusa.githubstats.Constants;

public class utils {

    public static boolean isNull(String string){
        return string == null || string.equals("");
    }

    public static int getCacheTime(){
        return Prefs.getInt(Constants.KEY_CACHETIME, 3600) * 1000; //To millis
    }
}
