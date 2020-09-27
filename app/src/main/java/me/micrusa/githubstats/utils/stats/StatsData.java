package me.micrusa.githubstats.utils.stats;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.util.ArrayList;

import me.micrusa.githubstats.utils.RequestsUtil;

public abstract class StatsData {

    protected boolean success;
    protected JSONObject response;
    protected int errorCode;
    protected ArrayList<Runnable> runOnResponse = new ArrayList<>();

    protected StatsData(String url){
        RequestsUtil.request(url, (isSuccess, response) -> {
            Logger.debug("Received response");
            success = isSuccess;
            if(!success)
                errorCode = Integer.parseInt(response);
            try {
                this.response = new JSONObject(response);
            } catch(Exception ignored) {}
            runAll();
        });
    }

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
            } catch (JSONException e) {
                Logger.error(e);
            }
            text.setText(newText);
        };
    }

}
