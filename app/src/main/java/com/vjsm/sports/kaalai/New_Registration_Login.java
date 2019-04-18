package com.vjsm.sports.kaalai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class New_Registration_Login extends AppCompatActivity {

    private EditText unames,upassd,uconfrpass,Uid,phones;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private Button uplod;
    private ProgressDialog progressDialog;
    private String username,userpassword,userconfirmpassword,userid,usermobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__registration__login);
        initialization();

        uplod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                username=unames.getText().toString().trim();
                userpassword=upassd.getText().toString().trim();
                userconfirmpassword=uconfrpass.getText().toString().trim();
                userid=Uid.getText().toString().trim();
                usermobile=phones.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    progressDialog.cancel();
                    unames.setError("பெயரை உள்ளிடவும்");
                }else if(TextUtils.isEmpty(userid)){
                    progressDialog.cancel();
                    Uid.setError("பயனர் ஐடி உள்ளிடவும்");
                }
                else if(TextUtils.isEmpty(userpassword)){
                    progressDialog.cancel();
                    upassd.setError("கடவுச்சொல்லை உள்ளிடவும்");
                }
                else if (TextUtils.isEmpty(userconfirmpassword)){
                    progressDialog.cancel();
                    uconfrpass.setError("உறுதி கடவுச்சொல்லை உள்ளிடவும்");
                }else if(TextUtils.isEmpty(usermobile)){
                    progressDialog.cancel();
                    phones.setError("மொபைல் நம்பரை உள்ளிடவும்");
                }else if (usermobile.length()<10){
                    phones.setError("Fill 10 digit mobile number");
                    progressDialog.cancel();
                }
                else{


                    if (userpassword.length()<5){
                        progressDialog.cancel();
                        upassd.setError("குறைந்தது 5 எழுத்துகளுக்கு மேல் இருக்க வேண்டும்.");
                    }else{
                        if(userpassword.equals(userconfirmpassword)){
                            progressDialog.show();
                            CollectionReference clf = fb.collection("JallikattuHUB_Auth");
                            Query q1 = clf.whereEqualTo("UserId", userid);
                            q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    boolean isExisting = false;
                                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        String Numbers = documentSnapshot.getString("UserId");
                                        if(userid.equals(Numbers)){
                                            isExisting=true;
                                            Uid.setError("இந்த பயனர் ஐடி ஏற்கனவே பயன்படுத்தபட்டது. ");
                                            progressDialog.cancel();
                                        }
                                    }
                                    if(!isExisting){
                                        String ph = "+91" + usermobile;
                                        progressDialog.cancel();
                                        Intent send = new Intent(New_Registration_Login.this, CodeVerification.class);
                                        send.putExtra("phone", ph);
                                        send.putExtra("phonenull",usermobile);
                                        send.putExtra("username",username);
                                        send.putExtra("password",userconfirmpassword);
                                        send.putExtra("userId",userid);
                                        startActivity(send);
                                        finish();
                                    }
                                }
                            });

                        }
                        else{
                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(),"சரியான கடவுச்சொல்லை உள்ளிடவும்",Toast.LENGTH_LONG).show();
                            upassd.setText("");
                            uconfrpass.setText("");
                        }
                    }

                }
            }
        });


    }

    private void initialization() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        unames=(EditText)findViewById(R.id.uname);
        upassd=(EditText)findViewById(R.id.upass);
        uconfrpass=(EditText)findViewById(R.id.ucpass);
        Uid=(EditText)findViewById(R.id.uid);
        phones=(EditText)findViewById(R.id.uphone);
        uplod=(Button)findViewById(R.id.store);
    }
}
