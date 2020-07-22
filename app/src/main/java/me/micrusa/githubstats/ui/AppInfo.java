package me.micrusa.githubstats.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import me.micrusa.githubstats.BuildConfig;
import me.micrusa.githubstats.R;

public class AppInfo extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        TextView appName = findViewById(R.id.appname);
        appName.setText(getResources().getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME);

        Button github = findViewById(R.id.github);
        Button paypal = findViewById(R.id.paypal);

        github.setOnClickListener(view -> {
            openLink("https://github.com/micrusa/SimpleGithubStats");
        });

        paypal.setOnClickListener(view -> {
            openLink("https://www.paypal.me/migueelcs");
        });
    }

    private void openLink(String url){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}