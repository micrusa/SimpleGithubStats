package me.micrusa.githubstats.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.User;
import me.micrusa.githubstats.utils.stats.UserData;
import me.micrusa.githubstats.utils.utils;

public class newUserActivity extends AppCompatActivity {

    private Button save;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        username = findViewById(R.id.newuser_username);
        save = findViewById(R.id.newuser_saveBtn);

        save.setOnClickListener(view -> {
            final String sUser = username.getText().toString();
            final User nonManagedUser = new User();
            nonManagedUser.setId(UUID.randomUUID().toString());
            nonManagedUser.setName(sUser);

            final UserData data = new UserData(nonManagedUser);

            data.addRunnable(() -> {
                if (utils.isNull(sUser) || !data.exists()) {
                    Toast.makeText(view.getContext(), R.string.invalidobject, Toast.LENGTH_SHORT).show();
                } else {
                    Realm realm = Realm.getDefaultInstance();

                    if(!realm.isInTransaction()) realm.beginTransaction();
                    realm.copyToRealm(nonManagedUser);
                    realm.commitTransaction();

                    finish();
                }
            });
        });
    }
}