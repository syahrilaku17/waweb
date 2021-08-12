package com.andro.whatswebapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        Ads ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(StartActivity.this, nativeAdContainer);

        ImageView ivShare = findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);
        ImageView ivReta = findViewById(R.id.iv_reta);
        ivReta.setOnClickListener(this);
        ImageView ivPrivecy = findViewById(R.id.iv_privecy);
        ivPrivecy.setOnClickListener(this);
        ImageView start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.iv_share:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
                break;

            case R.id.iv_reta:

                ratingDialog(StartActivity.this);
                break;

            case R.id.iv_privecy:

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://google.com/"));
                startActivity(i);
                break;


            default:
                break;
        }
    }

    public static void ratingDialog(Activity activity) {

        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(i3);
    }

    @Override
    public void onBackPressed() {
        new FancyGifDialog.Builder(this).setTitle("Rate This Application").setMessage("Thank you for using our app.If u really like out app Please rate us 5 stars")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground("#0b3540")
                .setPositiveBtnText("Ok").setNegativeBtnBackground("#FFA9A7A8").setGifResource(R.drawable.rateus).isCancellable(true).OnPositiveClicked(new FancyGifDialogListener() {
            public void OnClick() {
                Intent createChooser = Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())), "Share via");
                createChooser.setFlags(268435456);
                startActivity(createChooser);
            }
        }).OnNegativeClicked(new FancyGifDialogListener() {
            public void OnClick() {
                finishAffinity();
            }
        }).build();
    }


}
