package com.vjsm.sports.kaalai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Show extends Activity implements CustomAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    public static final String IMAGEU = "IMAGEURL";
    public static final String NAMESS = "NAME";
    public static final String  PLACE= "PLACE";
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String DISTRICT = "DISTRICT";
    public static final String STARTDAATE = "STARTDATE";
    public static final String MANTDATE = "MANTDATE";
    public static final String MADTDATE = "MADTDATE";
    public static final String PHONE = "PHONE";
    public static final String LocationLa = "LocationLa";
    public static final String LocationLo = "LocationLo";
ImageView count;
    private ImageView calender;
    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore fb;
    private ArrayList<Users> personNames;
private Spinner districtsearch;
    private AdView mAdView;

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
        setContentView(R.layout.activity_show);
        searchView=(EditText)findViewById(R.id.loading);
        districtsearch=(Spinner)findViewById(R.id.search);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Show.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.search_list));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtsearch.setAdapter(myAdapter);
        districtsearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Text=parent.getItemAtPosition(position).toString();
                if(Text.equals("மாவட்ட வாரியாக பார்க்க")){

                }else{
                    searchView.setText(Text);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


     MobileAds.initialize(this,
                "ca-app-pub-8502010865534472~2852825368");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        fb = FirebaseFirestore.getInstance();
        count=(ImageView) findViewById(R.id.Reff);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(null);
            }
        });
        calender=(ImageView)findViewById(R.id.cal);
        recyclerView = findViewById(R.id.recyclerView);
        personNames = new ArrayList<Users>();

vi=(TextView)findViewById(R.id.nodata);
        customAdapter = new CustomAdapter( personNames,this);
       final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        final String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
       customAdapter.setOnItemClickListener(Show.this);
        recyclerView.setAdapter(customAdapter);
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH-1);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

calender.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePicker = new DatePickerDialog(Show.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                String date2 = selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear;

          String text=(selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear);

                searchView.setText(text);

                // } else {
                // display("error");
                //}
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date To Search");
        mDatePicker.show();

    }
});

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
     //   dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();



    }

    private void getData() {
        mSwipeRefreshLayout.setRefreshing(true);
        fb.collection("Jallikattu").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(final QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error", e);
                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Users users = doc.getDocument().toObject(Users.class);
                        personNames.add(users);
                        if (personNames.size() > 0) {
                        }
                        customAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }



                if(personNames.isEmpty()){


                }


                Collections.sort(personNames, new Comparator<Users>() {
                    @Override
                    public int compare(Users o1, Users o2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

                        Date d1=null;
                        Date d2= null;

                        try {
                            d1=sdf.parse(String.valueOf(o1.getStartdate()));

                            d2= sdf.parse(String.valueOf(o2.getStartdate()));

                        } catch (ParseException e) {

                            e.printStackTrace();
                        }

                        if(d1 != null && d1.after(d2)){

                            return -1;

                        }else{

                            return 1;
                        }

                    }
                    // return o2.getStartdate().compareTo(o1.getStartdate());
                });
                if(personNames.isEmpty()){
                    recyclerView.setVisibility(View.INVISIBLE);
                    vi.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

        });
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<Users> Namesss = new ArrayList<>();
        for (Users s : personNames) {
            if (s.getPlace().contains(text)|| s.getDistrict().contains(text)||s.getStartdate().contains(text)) {
                Namesss.add(s);
            }
        }
        vi.setVisibility(View.INVISIBLE);
        customAdapter.filterList(Namesss);
        if (Namesss.isEmpty()){
vi.setText("நீங்கள் தேர்வு செய்த தேதி அல்லது மாவட்டத்தில் விழா எதும் இல்லை");
vi.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onItemClick(int position) {
Intent intent=new Intent(Show.this,Details.class);
Users detail =personNames.get(position);
intent.putExtra(IMAGEU,detail.getImageurl());
intent.putExtra(NAMESS,detail.getName());
intent.putExtra(PLACE,detail.getPlace());
intent.putExtra(DISTRICT,detail.getDistrict());
intent.putExtra(STARTDAATE,detail.getStartdate());
intent.putExtra(MANTDATE,detail.getMantdate());
intent.putExtra(MADTDATE,detail.getMadtdate());
intent.putExtra(PHONE,detail.getPhone());
intent.putExtra(LocationLa,detail.getMapLocationLa());
intent.putExtra(LocationLo,detail.getMapLocationLo());
startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(searchView.getText().toString().equals("")){
          finish();
      }
      else{
            searchView.setText("");
        }

    }

    @Override
    public void onRefresh() {
        personNames.clear();
        getData();
    }


}
