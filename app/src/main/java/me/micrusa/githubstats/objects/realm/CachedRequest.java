package me.micrusa.githubstats.objects.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CachedRequest extends RealmObject {

    @PrimaryKey
    private String url;

    private String cachedResponse;
    private long latestCache;

    public long getLatestCache(){
        return latestCache;
    }

    public void setLatestCache(long time){
        latestCache = time;
    }

    public String getCachedResponse() {
        return cachedResponse;
    }

    public void setCachedResponse(String cachedResponse) {
        this.cachedResponse = cachedResponse;
    }

}
