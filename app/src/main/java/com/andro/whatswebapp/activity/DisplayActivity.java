package com.andro.whatswebapp.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.andro.whatswebapp.adapter.CustomPagerrrrAdapter;
import com.andro.whatswebapp.adapter.StorieSaverrrVideoAdapter;
import com.andro.whatswebapp.models.VideosModel;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.List;

public class DisplayActivity extends StorieSaverBaseeeActivity {

    public List<VideosModel> arrayList = StorieSaverrrVideoAdapter.mVideoResponsesList2;
    ImageView copy;
    int currentPage;
    private String flag;
    int id;
    ImageView imgshare;
    private CustomPagerrrrAdapter mCustomPagerAdapter;

    public ViewPager mViewPager;
    ImageView movleft;
    ImageView movright;
    ImageView whatsappshareiv;

    AdView llAds;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_displaynew);
        llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(this.llAds, this);

        this.imgshare = (ImageView) findViewById(R.id.shareimggggg);
        this.movleft = (ImageView) findViewById(R.id.movleft);
        this.movright = (ImageView) findViewById(R.id.movright);
        this.copy = (ImageView) findViewById(R.id.copyy);
        this.whatsappshareiv = (ImageView) findViewById(R.id.whatsappshareiv);
        this.id = getIntent().getExtras().getInt("id", 0);
        this.flag = getIntent().getStringExtra("flag");
        setTitle(this.flag);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.currentPage = this.id;
        this.mCustomPagerAdapter = new CustomPagerrrrAdapter(this, this.arrayList);
        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        this.mViewPager.setAdapter(this.mCustomPagerAdapter);
        this.mViewPager.setCurrentItem(this.id);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
                //add
            }

            public void onPageScrolled(int i, float f, int i2) {
                //add
            }

            public void onPageSelected(int i) {
                //add
            }
        });
        this.movright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayActivity.this.mViewPager.setCurrentItem(DisplayActivity.this.mViewPager.getCurrentItem() + 1);
            }
        });
        this.movleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayActivity.this.mViewPager.setCurrentItem(DisplayActivity.this.mViewPager.getCurrentItem() - 1);
            }
        });
        this.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayActivity.this.doShareVideo(new File(((VideosModel) DisplayActivity.this.arrayList.get(DisplayActivity.this.mViewPager.getCurrentItem())).getVideoPath()));
            }
        });
        this.whatsappshareiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = DisplayActivity.this.mViewPager.getCurrentItem();
                DisplayActivity displayActivity = DisplayActivity.this;
                displayActivity.doShareWhatsappVideo(((VideosModel) displayActivity.arrayList.get(currentItem)).getVideoPath());
            }
        });
        this.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem = DisplayActivity.this.mViewPager.getCurrentItem();
                DisplayActivity displayActivity = DisplayActivity.this;
                displayActivity.saveImageStatus(((VideosModel) displayActivity.arrayList.get(currentItem)).getVideoPath());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
