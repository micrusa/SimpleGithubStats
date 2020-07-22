package me.micrusa.githubstats.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.util.ArrayList;

import io.realm.Realm;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.RepoRelease;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.ui.adapter.ReleaseAdapter;
import me.micrusa.githubstats.utils.RequestsUtil;

public class RepoReleases extends AppCompatActivity {

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_releases);
        String id = getIntent().getStringExtra(EXTRA_ID);
        Realm realm = Realm.getDefaultInstance();
        Repo repo = realm.where(Repo.class).equalTo(EXTRA_ID, id).findFirst();

        TextView name = findViewById(R.id.releases_repo_name);
        name.setText(repo.getName());

        String URL = "https://api.github.com/repos/" + repo.getRepo() + "/releases";
        RequestsUtil.request(URL, (isSuccess, response) -> {
            if(!isSuccess){
                Logger.error("Request failed with error code " + response);
                Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            Logger.debug("Received response " + response);
            ArrayList<RepoRelease> releases = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    RepoRelease release = new RepoRelease();
                    release.setName((String) object.get("name"));
                    release.setTag((String) object.get("tag_name"));
                    release.setAssets((JSONArray) object.get("assets"));
                    releases.add(release);
                    Logger.debug("Release length: " + releases.size());
                }

                ArrayAdapter<RepoRelease> adapter = new ReleaseAdapter(this, releases);
                ListView list = findViewById(R.id.listReleases);
                list.setAdapter(adapter);
            } catch (JSONException e) {
                Logger.error(e);
            }
        });
    }
}