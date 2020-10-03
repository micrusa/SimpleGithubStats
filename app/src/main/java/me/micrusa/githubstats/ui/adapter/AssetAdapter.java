package me.micrusa.githubstats.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.micrusa.githubstats.R;
import me.micrusa.githubstats.objects.ReleaseAsset;
import me.micrusa.githubstats.utils.Utils;

public class AssetAdapter extends ArrayAdapter<ReleaseAsset> {

    public AssetAdapter(Context context, ArrayList<ReleaseAsset> array) {
        super(context, 0, array);
    }

    private TextView name, downloads;
    private Button download;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReleaseAsset asset = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_release_asset, parent, false);
            init(convertView);

            name.setText(asset.getName());
            downloads.setText(String.valueOf(asset.getDownloads()));
            download.setOnClickListener(v -> Utils.openLink(asset.getDownloadURL(), v.getContext()));
        }

        return convertView;
    }

    private void init(View convertView){
        name = convertView.findViewById(R.id.asset_name);
        downloads = convertView.findViewById(R.id.asset_downloads);
        download = convertView.findViewById(R.id.asset_download);
    }

}
