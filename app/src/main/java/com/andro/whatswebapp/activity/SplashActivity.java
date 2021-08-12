package com.andro.whatswebapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.andro.whatswebapp.Adclick;
import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.github.ybq.android.spinkit.style.CubeGrid;

public class SplashActivity extends AppCompatActivity {
    private Ads ads;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.content_homesplash);

        ads  = new Ads();
        ads.interstitialload(this);

        ((ProgressBar) findViewById(R.id.progress)).setIndeterminateDrawable(new CubeGrid());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        SplashActivity splashActivity = SplashActivity.this;
                        splashActivity.startActivity(new Intent(splashActivity, StartActivity.class));
                        SplashActivity.this.finish();
                    }
                });
            }
        }, 3200);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
