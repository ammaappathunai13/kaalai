package com.vjsm.sports.kaalai;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Yshow extends Activity implements CustomAdapter.OnItemClickListener {
    public static final String IMAGEU = "IMAGEURL";
    public static final String NAMESS = "NAME";
    public static final String  PLACE= "PLACE";
    public static final String DISTRICT = "DISTRICT";
    public static final String STARTDAATE = "STARTDATE";
    public static final String MANTDATE = "MANTDATE";
    public static final String MADTDATE = "MADTDATE";
    public static final String PHONE = "PHONE";
    public static final String LocationLa = "LocationLa";

    public static final String LocationLo = "LocationLo";
    private AdView mAdView;

    ImageView count;
    private ImageView calender;
    private YCustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore fb;
    ProgressDialog progressDialog;
    private ArrayList<YUsers> personNames;
    private ProgressBar pro;
    private EditText searchView;
    String TAG = "hot";
    private RecyclerView mrecyclerView;
    String date;
    FirebaseDatabase firebaseDatabase;
    TextView vi;

    DatabaseReference dref;
    private CollectionReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yshow);

        MobileAds.initialize(this,
                "ca-app-pub-8502010865534472~2852825368");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        fb = FirebaseFirestore.getInstance();
        calender=(ImageView)findViewById(R.id.cal);
        recyclerView = findViewById(R.id.recyclerView);
        personNames = new ArrayList<YUsers>();
        searchView = findViewById(R.id.search);
        vi=(TextView)findViewById(R.id.nodata);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        customAdapter = new YCustomAdapter( personNames,this);
        final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        final String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        customAdapter.setOnItemClickListener(Yshow.this);
        recyclerView.setAdapter(customAdapter);
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH-1);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //   dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        fb.collection("JallikattuVideosYoutube").orderBy("Non",Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        YUsers Yus = documentSnapshot.toObject(YUsers.class);
                        personNames.add(Yus);
                        progressDialog.cancel();
                        if (personNames.size() > 0) {
                        }
                        customAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }





    @Override
    public void onItemClick(int position) {
        Intent s=new Intent(Yshow.this,YoutubeApi.class);
        s.putExtra("video",personNames.get(position).getVideoUrl());
        startActivity(s);
    }
}
