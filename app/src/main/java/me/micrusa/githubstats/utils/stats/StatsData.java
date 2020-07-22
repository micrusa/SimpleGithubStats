package me.micrusa.githubstats.utils.stats;

import android.content.Context;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.util.ArrayList;

import io.realm.RealmObject;

public abstract class StatsData {

    protected boolean success;
    protected JSONObject response;
    protected int errorCode;
    protected ArrayList<Runnable> runOnResponse = new ArrayList<>();

    public boolean exists(){
        return errorCode != 404;
    }

    public void addRunnable(Runnable r){
        if(response != null) {
            if (success) r.run();
        } else runOnResponse.add(r);
    }

    protected void setup(TextView text, String JSONName){
        addRunnable(getRunnable(text, JSONName));
    }

    protected void runAll(){
        for(Runnable r : runOnResponse)
            r.run();
    }

    private Runnable getRunnable(final TextView text, final String JSONName){
        return () -> {
            String newText = "ERROR";
            try {
                if(response != null)
                    newText = String.valueOf(response.get(JSONName));
                text.setText(newText);
            } catch (JSONException e) {
                text.setText(newText);
                Logger.error(e);
            }
        };
    }

}
