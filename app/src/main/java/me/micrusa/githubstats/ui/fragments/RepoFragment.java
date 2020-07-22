package me.micrusa.githubstats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.objects.realm.User;
import me.micrusa.githubstats.ui.adapter.RepoAdapter;
import me.micrusa.githubstats.ui.adapter.UserAdapter;
import me.micrusa.githubstats.ui.newRepoActivity;

public class RepoFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repos, container, false);

        //Set all users from realm DB
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Repo> reposRealm = realm.where(Repo.class).findAll();
        RepoAdapter repoAdapter = new RepoAdapter(root.getContext(), new ArrayList<>(reposRealm));
        ListView users = root.findViewById(R.id.list_repos);
        users.setAdapter(repoAdapter);

        //Setup button
        FloatingActionButton fab = root.findViewById(R.id.add_repo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), newRepoActivity.class));
            }
        });

        return root;
    }
}