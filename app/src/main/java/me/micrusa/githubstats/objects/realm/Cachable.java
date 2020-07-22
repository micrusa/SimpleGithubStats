package me.micrusa.githubstats.objects.realm;

public interface Cachable {
    String getId();
    long getLatestCache();
    void setLatestCache(long time);
    String getCachedResponse();
    void setCachedResponse(String cachedResponse);
}
