package com.vjsm.sports.kaalai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class YoutubeApi extends YouTubeBaseActivity {
    YouTubePlayerView myoutube;
    private InterstitialAd mInterstitialAd;

    Button clickplay;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api);
        MobileAds.initialize(this,
                "ca-app-pub-8502010865534472~2852825368");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        myoutube=(YouTubePlayerView)findViewById(R.id.view);
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

youTubePlayer.loadVideo(getIntent().getStringExtra("video"));
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        myoutube.initialize(YoutubeAPIConfig.getYoutbeApi(),onInitializedListener);

    }
}
