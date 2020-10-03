package me.micrusa.githubstats.utils.stats;

import android.widget.TextView;

import me.micrusa.githubstats.objects.realm.User;

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
