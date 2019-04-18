package com.vjsm.sports.kaalai;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.mbms.MbmsErrors;
import android.widget.Toast;

public class downlaodpage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downlaodpage);

        String url=getIntent().getStringExtra("videourl");
        DownloadManager downloadManager=(DownloadManager)getSystemService(this.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setTitle("video dowloaded");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        finish();

    }
}
