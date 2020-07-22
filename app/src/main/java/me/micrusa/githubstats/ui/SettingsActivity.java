package me.micrusa.githubstats.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import me.micrusa.githubstats.Constants;
import me.micrusa.githubstats.R;
import me.micrusa.githubstats.utils.Updater;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.settings));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference checkForUpdates = findPreference(Constants.KEY_CHECKUPDT);
            Preference appInfo = findPreference(Constants.KEY_APPINFO);
            checkForUpdates.setOnPreferenceClickListener(preference -> Updater.checkForUpdates(getContext()));
            appInfo.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(preference.getContext(), AppInfo.class));
                return true;
            });
        }
    }
}