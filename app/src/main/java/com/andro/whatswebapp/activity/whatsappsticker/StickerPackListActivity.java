package com.andro.whatswebapp.activity.whatsappsticker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.andro.whatswebapp.activity.MainActivity;
import com.google.android.gms.ads.AdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickerPackListActivity extends AddStickerPackkkActivity {
    public static final String EXTRA_STICKER_PACK_LIST_DATA = "sticker_pack_list";

    public StickerPackListAdapter allStickerPacksListAdapter;
    private ImageView ivBack;
    private AdView llAds;
    private final StickerPackListAdapter.OnAddButtonClickedListener onAddButtonClickedListener = new StickerPackListAdapter.OnAddButtonClickedListener() {
        public final void onAddButtonClicked(StickerPack stickerPack) {
            addStickerPackToWhatsApp(stickerPack.identifier, stickerPack.name);
        }
    };
    private LinearLayoutManager packLayoutManager;
    private RecyclerView packRecyclerView;
    private ArrayList<StickerPack> stickerPackList;
    private TextView tv_title;
    private WhiteListCheckAsyncTask whiteListCheckAsyncTask;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_sticker_pack_list);
        this.tv_title = (TextView) findViewById(R.id.title);
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.tv_title.setText("My Stickers");
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                StickerPackListActivity.this.startActivity(new Intent(StickerPackListActivity.this, MainActivity.class));
                StickerPackListActivity.this.finish();
            }
        });
        this.llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(this.llAds, this);
        this.packRecyclerView = (RecyclerView) findViewById(R.id.sticker_pack_list);
        this.stickerPackList = getIntent().getParcelableArrayListExtra(EXTRA_STICKER_PACK_LIST_DATA);
        showStickerPackList(this.stickerPackList);
    }


    @Override
    public void onResume() {
        super.onResume();
        this.whiteListCheckAsyncTask = new WhiteListCheckAsyncTask(this);
        WhiteListCheckAsyncTask whiteListCheckAsyncTask2 = this.whiteListCheckAsyncTask;
        ArrayList<StickerPack> arrayList = this.stickerPackList;
        whiteListCheckAsyncTask2.execute(arrayList.toArray(new StickerPack[arrayList.size()]));
    }


    @Override
    public void onPause() {
        super.onPause();
        WhiteListCheckAsyncTask whiteListCheckAsyncTask2 = this.whiteListCheckAsyncTask;
        if (whiteListCheckAsyncTask2 != null && !whiteListCheckAsyncTask2.isCancelled()) {
            this.whiteListCheckAsyncTask.cancel(true);
        }
    }

    private void showStickerPackList(List<StickerPack> list) {
        this.allStickerPacksListAdapter = new StickerPackListAdapter(list, this.onAddButtonClickedListener);
        this.packRecyclerView.setAdapter(this.allStickerPacksListAdapter);
        this.packLayoutManager = new LinearLayoutManager(this);
        this.packLayoutManager.setOrientation(1);
        this.packRecyclerView.addItemDecoration(new DividerItemDecoration(this.packRecyclerView.getContext(), this.packLayoutManager.getOrientation()));
        this.packRecyclerView.setLayoutManager(this.packLayoutManager);
        this.packRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                StickerPackListActivity.this.recalculateColumnCount();
            }
        });
    }



    public void recalculateColumnCount() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sticker_pack_list_item_preview_image_size);
        StickerPackListItemViewHolder stickerPackListItemViewHolder = (StickerPackListItemViewHolder) this.packRecyclerView.findViewHolderForAdapterPosition(this.packLayoutManager.findFirstVisibleItemPosition());
        if (stickerPackListItemViewHolder != null) {
            this.allStickerPacksListAdapter.setMaxNumberOfStickersInARow(Math.min(5, Math.max(stickerPackListItemViewHolder.imageRowView.getMeasuredWidth() / dimensionPixelSize, 1)));
        }
    }

    static class WhiteListCheckAsyncTask extends AsyncTask<StickerPack, Void, List<StickerPack>> {
        private final WeakReference<StickerPackListActivity> stickerPackListActivityWeakReference;

        WhiteListCheckAsyncTask(StickerPackListActivity stickerPackListActivity) {
            this.stickerPackListActivityWeakReference = new WeakReference<>(stickerPackListActivity);
        }


        public final List<StickerPack> doInBackground(StickerPack... stickerPackArr) {
            StickerPackListActivity stickerPackListActivity = (StickerPackListActivity) this.stickerPackListActivityWeakReference.get();
            if (stickerPackListActivity == null) {
                return Arrays.asList(stickerPackArr);
            }
            for (StickerPack stickerPack : stickerPackArr) {
                stickerPack.setIsWhitelisted(WhitelistCheck.isWhitelisted(stickerPackListActivity, stickerPack.identifier));
            }
            return Arrays.asList(stickerPackArr);
        }


        @Override
        public void onPostExecute(List<StickerPack> list) {
            StickerPackListActivity stickerPackListActivity = (StickerPackListActivity) this.stickerPackListActivityWeakReference.get();
            if (stickerPackListActivity != null) {
                stickerPackListActivity.allStickerPacksListAdapter.setStickerPackList(list);
                stickerPackListActivity.allStickerPacksListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
