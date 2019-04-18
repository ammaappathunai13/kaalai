package com.vjsm.sports.kaalai;

import android.app.DownloadManager;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class YCustomAdapter extends RecyclerView.Adapter<YCustomAdapter.MyViewHolder> {
        ArrayList<YUsers> personNames = new ArrayList<>();
private Yshow mListener;
        Context context;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;


    public interface OnItemClickListener {
    void onItemClick(int position);
}

    public void setOnItemClickListener(Yshow listener) {
        mListener =  listener;
    }

    public YCustomAdapter(ArrayList<YUsers> personNames, Context context) {

        this.personNames = personNames;
        this.context = context;
    }


    @Override
    public YCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.yrow, parent, false);
        YCustomAdapter.MyViewHolder vh = new YCustomAdapter.MyViewHolder(v); // pass the view to View Holder

        return vh;
    }


    @Override
    public void onBindViewHolder(final YCustomAdapter.MyViewHolder holder, final int position) {
       // holder.name.setText(personNames.get(position).getName());
     //   holder.Description.setText(personNames.get(position).getDescripition());
       // holder.sdate.setText("தேதி: "+personNames.get(position).getStartdate());
        // holder.madtdate.setText(personNames.get(position).getMadtdate());
        //  holder.mantdate.setText(personNames.get(position).getMantdate());
            //holder.viewss.setText(personNames.get(position).getViews());


        YouTubePlayer.OnInitializedListener onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(personNames.get(position).getVideoUrl());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


holder.Description.setText(personNames.get(position).getDec());

       // Toast.makeText(context,personNames.get(position).getVideoUrl(),Toast.LENGTH_LONG).show();
    //    long interval = 5000 * 1000;
      //  String title="https://www.googleapis.com/youtube/v3/videos?key="+YoutubeAPIConfig.getYoutbeApi()+"&part=snippet&id="+personNames.get(position).getVideoUrl()+"";
        String urls= "http://img.youtube.com/vi/"+personNames.get(position).getVideoUrl()+"/mqdefault.jpg";
        Glide.with(context).load(urls).into(holder.imagev);

  //  holder.store.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
            //   Toast.makeText(context,"Downloading",Toast.LENGTH_LONG).show();
            //    Intent s=new Intent(context,downlaodpage.class);
           //     s.putExtra("videourl",personNames.get(position).getVideoUrl());
          //      context.startActivity(s);
         //   }
     //   });
    //holder.duration.setText(durartion);
        //Log.d("videourl",personNames.get(position).getVideoUrl());


}


    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public void filterList(ArrayList<YUsers> names) {
        this.personNames = names;
        notifyDataSetChanged();
    }
public class MyViewHolder extends RecyclerView.ViewHolder {
    YouTubeThumbnailView youTubeThumbnailView;
    TextView name, place, district, viewss, madtdate, mantdate, phone,Description,duration;
    ImageView imagev;
    RelativeLayout relativeLayout;
    YouTubeThumbnailView thumbnailView;
    View mview;
    YouTubeThumbnailView.OnInitializedListener onInitializedListener;
    Button store;
 private DownloadManager downloadManager;
 WebView videoviews;
     VideoView videoView;
    public MyViewHolder(final View itemView) {
        super(itemView);
        mview = itemView;

       // youTubeThumbnailView = (YouTubeThumbnailView) mview.findViewById(R.id.thum);
     Description = (TextView) mview.findViewById(R.id.titlee);
     //   sdate = (TextView) mview.findViewById(R.id.stdate);
        //   madtdate = (TextView) mview.findViewById(R.id.madTdate);
        //   mantdate = (TextView) mview.findViewById(R.id.manTdate);
        //   phone = (TextView) mview.findViewById(R.id.phonenumber);
        imagev=(ImageView)mview.findViewById(R.id.iamgeviews);
      //  store=(Button)mview.findViewById(R.id.Download);
//viewss=(TextView)mview.findViewById(R.id.views) ;
        // relativeLayout = (RelativeLayout) mview.findViewById(R.id.cliccc);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });

    }


}
}
