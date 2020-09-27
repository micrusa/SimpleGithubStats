package me.micrusa.githubstats.utils.stats;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import io.realm.Realm;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.utils.RequestsUtil;
import me.micrusa.githubstats.utils.utils;

public class RepoData extends StatsData {

    public RepoData(final Repo repo) {
        super("https://api.github.com/repos/" + repo.getRepo());
    }

    public void setStars(TextView text){
        super.setup(text, "stargazers_count");
    }

    public void setIssues(TextView text){
        super.setup(text, "open_issues_count");
    }

    public void setForks(TextView text){
        super.setup(text, "forks_count");
    }

    public void setWatchers(TextView text){
        super.setup(text, "watchers_count");
    }
}
