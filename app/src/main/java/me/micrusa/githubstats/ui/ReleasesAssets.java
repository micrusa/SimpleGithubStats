package me.micrusa.githubstats.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.util.ArrayList;

import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.ReleaseAsset;
import me.micrusa.githubstats.ui.adapter.AssetAdapter;

public class ReleasesAssets extends AppCompatActivity {

    public static final String EXTRA_ASSETS = "assets";
    public static final String EXTRA_NAME = "name";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_releases);

        String assets = getIntent().getStringExtra(EXTRA_ASSETS);
        String name = getIntent().getStringExtra(EXTRA_NAME);

        TextView nameText = findViewById(R.id.releases_repo_name);
        nameText.setText(name);
        try {
            fillList(new JSONArray(assets));
        } catch (JSONException e) {
            Logger.error(e);
        }
    }

    private void fillList(JSONArray assets) throws JSONException {
        ArrayList<ReleaseAsset> assetsArr = new ArrayList<>();

        for(int i = 0; i < assets.length(); i++){
            ReleaseAsset asset = new ReleaseAsset();
            JSONObject assetObj = assets.getJSONObject(i);
            asset.setName((String) assetObj.get("name"));
            asset.setDownloadURL((String) assetObj.get("browser_download_url"));
            asset.setDownloads((int) assetObj.get("download_count"));
            assetsArr.add(asset);
        }

        AssetAdapter adapter = new AssetAdapter(this, assetsArr);
        ListView list = findViewById(R.id.listReleases);
        list.setAdapter(adapter);
    }

}
