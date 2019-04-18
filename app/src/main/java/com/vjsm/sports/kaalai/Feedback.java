package com.vjsm.sports.kaalai;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends Activity {
    EditText feedbacks;
    private AlertDialog.Builder builder;

    FirebaseFirestore fb;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback");
        builder = new AlertDialog.Builder(this);

        fb = FirebaseFirestore.getInstance();
        feedbacks = (EditText) findViewById(R.id.feedback);
        submit = (Button) findViewById(R.id.clickx);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feed = feedbacks.getText().toString().trim();
                if (TextUtils.isEmpty(feed)){
                    feedbacks.setError("please fill feed back");
                }
                else{
                    String phone=getIntent().getStringExtra("send");
                    Map<String, Object> feedback = new HashMap<>();
                    feedback.put("FeedBack", feed);
                    feedback.put("Phone",phone);
                    fb.collection("FeedBack").document().set(feedback).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            display("error");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }
        });

    }

    public void display(String code) {
        if (code.equals("error")) {
            builder.setTitle("Thanks for your Feedback");

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            finish();
                    return;
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
