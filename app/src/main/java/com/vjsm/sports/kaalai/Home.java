package com.vjsm.sports.kaalai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static android.content.Intent.ACTION_VIEW;

public class Home extends Fragment implements  UpdateHelper.onUpdateCheckLinstener{
    ImageView Ci, Ki, Fi, Vi;
    TextView cc, kc, fc, vc;
    private AdView mAdView;


    private AlertDialog.Builder builder;

    String language,logouts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, container, false);

        MobileAds.initialize(getContext(),
                "ca-app-pub-8502010865534472~2852825368");
        mAdView =v. findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



// TODO: Add adView to your view hierarchy.

        UpdateHelper.with(getContext()).onUpdateChecker(this).check();
        builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Do You want Logout ?");

        if (getArguments()!=null){
            Bundle b=this.getArguments();

            logouts=b.getString("logout");
        }
        if (logouts!=null){

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
                    sharedPreferences=getActivity().getSharedPreferences("com.vjsm.sports.kaalai",Context.MODE_PRIVATE);
                    editor=sharedPreferences.edit();
                    editor.clear().commit();
                    Intent sss=new Intent(getContext(),Login_Page.class);
                    startActivity(sss);
                    ActivityCompat.finishAffinity(getActivity());


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();




        }else{

        }

        cc = (TextView) v.findViewById(R.id.cd);
        kc = (TextView) v.findViewById(R.id.kd);
        fc = (TextView) v.findViewById(R.id.fd);
        vc = (TextView) v.findViewById(R.id.vd);
        Ci = (ImageView) v.findViewById(R.id.c);
        Ki = (ImageView) v.findViewById(R.id.k);
        Fi = (ImageView) v.findViewById(R.id.f);
        Vi = (ImageView) v.findViewById(R.id.v);
        kc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("send");
                Intent intent2 = new Intent(getActivity(), Show.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("send");
                Intent intent2 = new Intent(getActivity(), Yshow.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Yshow.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Upload.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        Ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Feedback.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        Ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Upload.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        Ki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Show.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        Fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Yshow.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });
        Vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gets = getActivity().getIntent().getStringExtra("phones");
                Intent intent2 = new Intent(getActivity(), Feedback.class);
                intent2.putExtra("send", gets);
                startActivity(intent2);
            }
        });


        return v;



    }
    public void onBackPressed () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TourantsHUB");

        builder.setIcon(R.drawable.playstore_icon);
        builder.setMessage("Do you Want  Exit ...?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        getActivity().finish();
                        System.exit(0);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onUpdateCheckListener(String urlApp) {

        AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                .setTitle("New Version Available")
                .setMessage("Please Update...")
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = urlApp;

                        Intent i = new Intent(ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();

    }
}


