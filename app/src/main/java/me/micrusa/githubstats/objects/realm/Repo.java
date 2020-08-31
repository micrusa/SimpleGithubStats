package me.micrusa.githubstats.objects.realm;

import androidx.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Repo extends RealmObject {
    @PrimaryKey
    private String id;

    private String author;
    private String name;
    //Cached items
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepo(){
        return author + "/" + name;
    }

    public String getCachedResponse() {
        return cachedResponse;
    }

    public void setCachedResponse(String cachedResponse) {
        this.cachedResponse = cachedResponse;
    }

}
