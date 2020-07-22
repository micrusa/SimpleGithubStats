package me.micrusa.githubstats.objects.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Cachable {
    @PrimaryKey
    private String id;

    private String name;
    //Cached data
    private String cachedResponse;
    private long latestCache;

    public long getLatestCache(){
        return latestCache;
    }

    public void setLatestCache(long time){
        latestCache = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCachedResponse() {
        return cachedResponse;
    }

    public void setCachedResponse(String cachedResponse) {
        this.cachedResponse = cachedResponse;
    }

}
