package com.vjsm.sports.kaalai;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Login_Page extends Activity {
    private TextView newUser;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private EditText Username,Password;
    private SharedPreferences.Editor editor;
    private String usernames,passwords;
    private boolean isExisting;
    private Button Login;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        inilization();
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences=getSharedPreferences("com.vjsm.sports.kaalai",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if((!sharedPreferences.getString(getString(R.string.UserId),"").equals("")))
        {
            if(!(sharedPreferences.getString(getString(R.string.Password),"")).equals("")){
                Intent s=new Intent(Login_Page.this,Home_PageLoader.class);
                String userId=getString(R.string.UserId);
                String language=getString(R.string.Language);
                s.putExtra("userId",userId);
                s.putExtra("language",language);
                startActivity(s);
                this.finish();
            }
        }else{
            checksharedprefrence();
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    usernames=Username.getText().toString().trim();
                    passwords=Password.getText().toString().trim();


                    if (TextUtils.isEmpty(usernames)){
                        progressDialog.cancel();
                        Username.setError("பயனர் ஐடி உள்ளிடுக.");
                    }else if(TextUtils.isEmpty(passwords)){
                        progressDialog.cancel();
                        Password.setError("கடவுச்சொல்லை உள்ளிடுக.");
                    }else{
                        final DocumentReference drf = fb.collection("JallikattuHUB_Auth").document(usernames);

                        progressDialog.show();
                        final CollectionReference clf = fb.collection("JallikattuHUB_Auth");
                        Query q1 = clf.whereEqualTo("UserId", usernames);
                        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                 isExisting = false;
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    String Numbers = documentSnapshot.getString("UserId");
                                    if(usernames.equals(Numbers)){
                                        isExisting=true;
                                        drf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    String Numbers = document.getString("Password");
                                                    String Name=document.getString("UserName");
                                                    String Number=document.getString("PhoneNumber");
                                                    if (document.exists()) {
                                                        if (document.getString("Password").equals(passwords)){
                                                            editor.putString(getString(R.string.UserId),usernames);
                                                            editor.commit();

                                                            editor.commit();
                                                            editor.putString(getString(R.string.Password),passwords);
                                                            editor.commit();
                                                            editor.putString(getString(R.string.LoginUserName),Name);
                                                            editor.commit();
                                                            editor.putString(getString(R.string.LoginUser_PhoneNumber),Number);
                                                            editor.commit();
                                                            isExisting=true;
                                                            Intent i=new Intent(Login_Page.this,Home_PageLoader.class);
                                                            i.putExtra("username",usernames);
                                                            i.putExtra("LoginUserName",Name);
                                                            i.putExtra("LoginPhoneNumber",Number);
                                                            startActivity(i);
                                                            progressDialog.cancel();
                                                            finish();
                                                        }
                                                        else{
                                                            progressDialog.cancel();
                                                            Password.setError("தவறான கடவுச்சொல்");
                                                            Toast.makeText(getApplicationContext(),"தவறான கடவுச்சொல்...! சரியான கடவுச்சொல்லை உள்ளிடவும்.",Toast.LENGTH_LONG).show();

                                                        }
                                                            Log.d("Ck", "DocumentSnapshot data: " + document.getString("Password"));
                                                    } else {
                                                        Log.d("Ck", "No such document");
                                                    }
                                                } else {
                                                    Log.d("Ck", "get failed with ", task.getException());
                                                }
                                            }
                                        });

                                    }
                                }
                                if(!isExisting){
                                    progressDialog.cancel();
                                    Username.setError("தவறான பயனர் ஐடி");
                                    Toast.makeText(getApplicationContext(),"தவறான பயனர் ஐடி...! சரியான பயனர் ஐடி  உள்ளிடவும்.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }

                }
            });
        }



        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent s=new Intent(Login_Page.this,New_Registration_Login.class);
                startActivity(s);
                progressDialog.cancel();

            }
        });
    }
    private void checksharedprefrence() {
        String UserId=sharedPreferences.getString(getString(R.string.UserId),"");
        String Password=sharedPreferences.getString(getString(R.string.Password),"");
        String Language=sharedPreferences.getString(getString(R.string.Language),"");

    }
    private void inilization() {
        progressDialog=new ProgressDialog(Login_Page.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        newUser=(TextView)findViewById(R.id.newuser);
        Username=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);
        Login=(Button)findViewById(R.id.login);

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_Page.this);
        builder.setTitle("NVP");

        builder.setIcon(R.drawable.playstore_icon);
        builder.setMessage("Do you Want  Exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();

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


}
