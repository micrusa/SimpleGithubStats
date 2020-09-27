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
        super("https://api.github.com/users/" + user.getName());
    }

    public void setFollowers(TextView text){
        super.setup(text, "followers");
    }

    public void setRepos(TextView text){
        super.setup(text, "public_repos");
    }

}
