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

    private ListView users;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_users, container, false);

        users = root.findViewById(R.id.list_users);
        loadListView();

        //Setup button
        FloatingActionButton fab = root.findViewById(R.id.add_user);
        fab.setOnClickListener(view -> startActivity(new Intent(view.getContext(), newUserActivity.class)));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadListView();
    }

    private void loadListView(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<User> usersRealm = realm.where(User.class).findAll();
        UserAdapter userAdapter = new UserAdapter(root.getContext(), new ArrayList<>(usersRealm));
        users.setAdapter(userAdapter);
    }
}