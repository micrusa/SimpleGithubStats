package me.micrusa.githubstats.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import io.realm.Realm;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.utils.stats.RepoData;
import me.micrusa.githubstats.utils.utils;

public class newRepoActivity extends AppCompatActivity {

    private EditText username, repo;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_repo);

        username = findViewById(R.id.newrepo_username);
        repo = findViewById(R.id.newrepo_repo);
        save = findViewById(R.id.newrepo_saveBtn);

        save.setOnClickListener(view -> {
            final String sUser = username.getText().toString();
            final String sRepo = repo.getText().toString();
            final Repo nonManagedRepo = new Repo();
            nonManagedRepo.setId(UUID.randomUUID().toString());
            nonManagedRepo.setAuthor(sUser);
            nonManagedRepo.setName(sRepo);

            final RepoData repoData = new RepoData(nonManagedRepo);
            repoData.addRunnable(() -> {
                if (utils.isNull(sUser) || utils.isNull(sRepo) || !repoData.exists()) {
                    Toast.makeText(view.getContext(), R.string.invalidobject, Toast.LENGTH_SHORT).show();
                } else {
                    Realm realm = Realm.getDefaultInstance();

                    if (!realm.isInTransaction()) realm.beginTransaction();
                    realm.copyToRealm(nonManagedRepo);
                    realm.commitTransaction();

                    finish();
                }
            });
        });

    }
}