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
import me.micrusa.githubstats.objects.realm.User;
import me.micrusa.githubstats.ui.adapter.UserAdapter;
import me.micrusa.githubstats.ui.newUserActivity;

public class UserFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users, container, false);

        //Set all users from realm DB
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<User> usersRealm = realm.where(User.class).findAll();
        UserAdapter userAdapter = new UserAdapter(root.getContext(), new ArrayList<>(usersRealm));
        ListView users = root.findViewById(R.id.list_users);
        users.setAdapter(userAdapter);

        //Setup button
        FloatingActionButton fab = root.findViewById(R.id.add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), newUserActivity.class));
            }
        });

        return root;
    }
}