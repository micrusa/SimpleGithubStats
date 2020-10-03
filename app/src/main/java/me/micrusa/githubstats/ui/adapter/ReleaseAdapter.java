package me.micrusa.githubstats.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.util.ArrayList;

import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.RepoRelease;
import me.micrusa.githubstats.objects.realm.Repo;
import me.micrusa.githubstats.ui.ReleasesAssets;

public class ReleaseAdapter extends ArrayAdapter<RepoRelease> {

    public ReleaseAdapter(Context context, ArrayList<RepoRelease> release) {
        super(context, 0, release);
    }

    private TextView name, downloads, tag;
    private Button download;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RepoRelease release = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_release, parent, false);

            init(convertView);
            name.setText(release.getName());
            tag.setText(release.getTag());
            try {
                int downloadsNo = 0;
                for (int i = 0; i < release.getAssets().length(); i++) {
                    JSONObject object = release.getAssets().getJSONObject(i);
                    downloadsNo += (int) object.get("download_count");
                }
                downloads.setText(String.valueOf(downloadsNo));
            } catch (JSONException e) {
                Logger.error(e);
            }

            download.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ReleasesAssets.class);
                intent.putExtra(ReleasesAssets.EXTRA_ASSETS, release.getAssets().toString());
                intent.putExtra(ReleasesAssets.EXTRA_NAME, release.getName());
                v.getContext().startActivity(intent);
            });
        }

        return convertView;
    }

    private void init(View convertView){
        name = convertView.findViewById(R.id.release_name);
        downloads = convertView.findViewById(R.id.release_downloads);
        tag = convertView.findViewById(R.id.release_tag);
        download = convertView.findViewById(R.id.release_download);
    }

}
