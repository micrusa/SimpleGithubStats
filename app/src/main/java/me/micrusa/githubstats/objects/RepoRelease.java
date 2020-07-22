package me.micrusa.githubstats.objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class RepoRelease {

    private String name;
    private String tag;
    private JSONArray assets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public JSONArray getAssets() {
        return assets;
    }

    public void setAssets(JSONArray assets) {
        this.assets = assets;
    }

}
