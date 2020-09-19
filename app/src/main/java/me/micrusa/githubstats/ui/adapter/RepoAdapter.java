package me.micrusa.githubstats.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.ui.RepoReleases;
import me.micrusa.githubstats.utils.stats.RepoData;

public class RepoAdapter extends ArrayAdapter<Repo> {

    public RepoAdapter(Context context, ArrayList<Repo> repos) {
        super(context, 0, repos);
    }

    private TextView name, stars, issues, forks, watchers;

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Repo repo = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_repo, parent, false);

        init(convertView);

        name.setText(repo.getRepo());

        RepoData data = new RepoData(repo);
        data.setStars(stars);
        data.setIssues(issues);
        data.setForks(forks);
        data.setWatchers(watchers);

        convertView.setOnLongClickListener(view -> {
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction()) realm.beginTransaction();
            RepoAdapter.super.remove(repo);
            repo.deleteFromRealm();
            realm.commitTransaction();
            realm.close();
            notifyDataSetChanged();
            return true;
        });

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RepoReleases.class);
            intent.putExtra(RepoReleases.EXTRA_ID, repo.getId());
            view.getContext().startActivity(intent);
        });

        return convertView;
    }

    private void init(View convertView){
        name = convertView.findViewById(R.id.repo_name);
        stars = convertView.findViewById(R.id.repo_stars);
        issues = convertView.findViewById(R.id.repo_issues);
        forks = convertView.findViewById(R.id.repo_forks);
        watchers = convertView.findViewById(R.id.repo_watchers);
    }

}
