package com.vjsm.sports.kaalai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Maped extends AppCompatActivity implements OnMapReadyCallback  {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            init();
        }
    }
    public static final String SName = "name";
    public static final String SPlace = "place";
    public static final String SDistrict = "District";
    public static final String SMADDate = "maddate";
    public static final String SMANDate = "mandate";
    public static final String SSTDate = "startDate";
    public static final String PHONE = "Phonenumber";
    private AutoCompleteTextView mSearchText;
    private static final String TAG = "Maped";
    private String names,place,district,startdate,mantdate,madtdate,phone;
    private Button button;

    private static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private TextView hint;
private Button refresh;
    private GoogleApiClient mGoogleApiClient;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maped);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        button = (Button) findViewById(R.id.saveinstance);
        refresh=(Button)findViewById(R.id.Refersh);
        hint=(TextView)findViewById(R.id.hint);
        getLocationPermission();

        Intent intent = getIntent();
        names = intent.getStringExtra(Upload.Name);
         place = intent.getStringExtra(Upload.Place);
         district = intent.getStringExtra(Upload.District);
        startdate = intent.getStringExtra(Upload.StDate);
         mantdate = intent.getStringExtra(Upload.ManDate);
     madtdate = intent.getStringExtra(Upload.MadDate);
   phone = intent.getStringExtra(Upload.PhoneNumber);

refresh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        recreate();
        refresh.setVisibility(View.INVISIBLE);
        hint.setVisibility(View.VISIBLE);
    }
});
    }

    private void init() {

        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");


        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Maped.this);
        List<Address> list = new ArrayList<>();
        try {


            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
            button = (Button) findViewById(R.id.saveinstance);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Clicked current", Toast.LENGTH_SHORT).show();
                }
            });
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));


        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
hint.setVisibility(View.VISIBLE);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Maped.this);
        if (ActivityCompat.checkSelfPermission(Maped.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            return;
        }
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(Maped.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
if(location!=null){
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cSend(location.getLongitude(),location.getLatitude());

        }
    });


}
            }
        });
        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                                Log.d("CUrrent LOCation", String.valueOf(currentLocation));


                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,"My Location");


                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            refresh.setVisibility(View.VISIBLE);
                            Toast.makeText(Maped.this, "Location-ஜ ஆன் செய்யவும்", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                          startActivity(intent);
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom,String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aSend(latLng.longitude,latLng.latitude);
                }
            });



        }

       hideSoftKeyboard();
    }




    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Maped.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {FINE_LOCATION,
                COURSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private void cSend(double longitude, double latitude) {
        String ds=String.valueOf(longitude);
        String es=String.valueOf(latitude);
        Intent intent=new Intent(Maped.this,Upload.class);

        intent.putExtra(SName,names);
        intent.putExtra(SPlace,place);
        intent.putExtra(SSTDate,startdate);
        intent.putExtra(SDistrict,district);
        intent.putExtra(SMANDate,mantdate);
        intent.putExtra(SMADDate,madtdate);
        intent.putExtra(PHONE,phone);
        startActivity(intent);

        intent.putExtra("latitude",ds);
        intent.putExtra("langitude",es);
        startActivity(intent);
        finish();
    }
    private void aSend(double longitude, double latitude) {
        String ss=String.valueOf(latitude);
        String as=String.valueOf(longitude);
        Intent intent=new Intent(Maped.this,Upload.class);
        intent.putExtra(SName,names);
        intent.putExtra(SPlace,place);
        intent.putExtra(SSTDate,startdate);
        intent.putExtra(SDistrict,district);
        intent.putExtra(SMANDate,mantdate);
        intent.putExtra(SMADDate,madtdate);
        intent.putExtra(PHONE,phone);
        startActivity(intent);


        intent.putExtra("alatitude",ss);
        intent.putExtra("alangitude",as);
        startActivity(intent);
        finish();
    }





}

