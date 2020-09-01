package me.micrusa.githubstats.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import me.micrusa.githubstats.BuildConfig;
import me.micrusa.githubstats.R;

public class Updater {

    private static final String UPDATE_URL = "https://api.github.com/repos/micrusa/SimpleGithubStats/releases/latest";

    public static boolean checkForUpdates(Context context){
        RequestsUtil.request(UPDATE_URL, (isSuccess, response) -> {
            if (!isSuccess){
                Logger.error("Failed checking for updates with response code " + response);
                if(!response.equals("403"))
                    Toast.makeText(context, context.getResources().getString(R.string.updatefail), Toast.LENGTH_LONG).show();
                return;
            } else if (context == null){
                Logger.error("Null context!");
                return;
            }
            try {
                JSONObject json = new JSONObject(response);
                String latestVersion = (String) json.get("tag_name");
                String currentVersion = "v" + BuildConfig.VERSION_NAME;
                String toast;
                if(!currentVersion.equals(latestVersion)){
                    String dlUrl = (String) json.get("html_url");
                    Logger.info("Update available! URL: \"" + dlUrl +
                            "\", remote version: " + latestVersion + ", local version: " + currentVersion);
                    toast = context.getResources().getString(R.string.update).replace("$v", latestVersion);
                } else
                    toast = context.getResources().getString(R.string.noupdate);
                Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Logger.error(e);
            }
        });
        return true;
    }
}
