package me.micrusa.githubstats.utils.stats;

import android.widget.TextView;

import me.micrusa.githubstats.objects.realm.Repo;

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
