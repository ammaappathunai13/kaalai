package com.vjsm.sports.kaalai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Deletion_Activity extends Fragment  implements ListCustomAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener{

    private AlertDialog.Builder builder;
    private AdView mAdView;

    public static final String IMAGEU = "IMAGEURL";
    public static final String NAMESS = "NAME";
    public static final String PLACE = "PLACE";
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
    private ListCustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore fb;
    private ArrayList<Users> personNames;
    private ProgressBar pro;
    private EditText searchView;
    String TAG = "hot";
    private RecyclerView mrecyclerView;
    String date;
    FirebaseDatabase firebaseDatabase;
    TextView vi;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String UserId;
    DatabaseReference dref;
    private CollectionReference ref;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.deletion_activity,container,false);
        builder = new AlertDialog.Builder(getContext());
        MobileAds.initialize(getContext(),
                "ca-app-pub-8502010865534472~2852825368");
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences = getActivity().getSharedPreferences("com.vjsm.sports.kaalai", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UserId=sharedPreferences.getString(getString(R.string.UserId),"");

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(Deletion_Activity.this);

        fb = FirebaseFirestore.getInstance();


        recyclerView = v.findViewById(R.id.recyclerView);
        personNames = new ArrayList<Users>();
        searchView = v.findViewById(R.id.search);
        vi = (TextView) v.findViewById(R.id.nodata);
        customAdapter = new ListCustomAdapter(personNames, getContext());

        customAdapter.setOnItemClickListener(this);
        final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        final String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        recyclerView.setAdapter(customAdapter);
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH - 1);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //   dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getdata();




        return v;
    }

    private void getdata() {
        mSwipeRefreshLayout.setRefreshing(true);

        final CollectionReference clf = fb.collection("Jallikattu");
        Query q1 = clf.whereEqualTo("UserId", UserId);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String id = documentSnapshot.getString("UserId");
                    if (id.equals(UserId)) {
                        personNames.clear();
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
                    }




                }

                if (personNames.isEmpty()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.INVISIBLE);
                    vi.setVisibility(View.VISIBLE);
                }


            }
        });

    }

    @Override
    public void onRefresh() {
        personNames.clear();
        getdata();
    }

    @Override
    public void onItemClick(int position) {
        String  user=personNames.get(position).getId();

        final CollectionReference clf = fb.collection("Jallikattu");
        Query q1 = clf.whereEqualTo("Id", user);

        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String id = documentSnapshot.getString("Id");

                    String delete=  documentSnapshot.getId();
                    display(delete,"delete");
                }

            }
        });


    }

    private void display(String delete, String ref) {
        if (ref.equals("delete")) {
            builder.setTitle("Warning");
            builder.setMessage(" Do you want delete ? ");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    fb=FirebaseFirestore.getInstance();
                    fb.collection("Jallikattu").document(delete).delete();
onRefresh();

                }
            }).setNegativeButton("No",null);
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
