package me.micrusa.githubstats;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {
  
  private static Application mApp;
  
  public static Application getApp(){
    return mApp;
  }

    @Override
    public void onCreate() {
        super.onCreate();
        setupRealm();
        setupEasyprefs();
        mApp = this;
    }

    private void setupRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("GithubStats.realm")
                .schemaVersion(2)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void setupEasyprefs(){
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

}
