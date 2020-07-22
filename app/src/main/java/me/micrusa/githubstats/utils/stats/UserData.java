package me.micrusa.githubstats.utils.stats;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import io.realm.Realm;
import me.micrusa.githubstats.objects.realm.User;
import me.micrusa.githubstats.utils.RequestsUtil;
import me.micrusa.githubstats.utils.utils;

public class UserData extends StatsData {

    public UserData(final User user){
        if(user.getCachedResponse() != null  && System.currentTimeMillis() - user.getLatestCache() <= utils.getCacheTime()){
            Logger.debug("Using cached data");
            try {
                response = new JSONObject(user.getCachedResponse());
            } catch(JSONException ignored) {}
            success = true;
            runAll();
            return;
        }

        String url = "https://api.github.com/users/" + user.getName();

        RequestsUtil.request(url, (isSuccess, response) -> {
            Logger.debug("Received response");
            success = isSuccess;
            if(!success) errorCode = Integer.parseInt(response);
            try {
                UserData.super.response = new JSONObject(response);
                cache(user, response);
            } catch(Exception ignored) {}
            runAll();
        });
    }

    public void setFollowers(TextView text){
        super.setup(text, "followers");
    }

    public void setRepos(TextView text){
        super.setup(text, "public_repos");
    }

    private void cache(User user, String response){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User managedUser = realm.where(User.class).equalTo("id", user.getId()).findFirst();
        managedUser.setCachedResponse(response); //No possible NullPointerException because it's in the fragment
        managedUser.setLatestCache(System.currentTimeMillis());
        realm.commitTransaction();
    }

}
