package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowWorkerRequestInfo2 extends AppCompatActivity {
TextView tv_custName,tv_custAddress,tv_custContact,tv_bookingStatus;
BookWorkerInfo bookWorkerInfo;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
WorkerInfo workerInfo;
Button btn_accept,btn_reject;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_worker_request_info2);

        tv_custName=findViewById(R.id.tv_custName);
        tv_custAddress=findViewById(R.id.tv_custAddress);
        tv_custContact=findViewById(R.id.tv_custContact);
        tv_bookingStatus=findViewById(R.id.tv_bookingStatus);
        btn_accept=findViewById(R.id.btn_accept);
        btn_reject=findViewById(R.id.btn_reject);

        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("bookinfo","");
        if (!json.equals("")) {
            bookWorkerInfo = gson.fromJson(json, BookWorkerInfo.class);
        }

        tv_custName.setText(bookWorkerInfo.getCustomerInfo().getName());
        tv_custAddress.setText(bookWorkerInfo.getCustomerInfo().getAddress());
        tv_custContact.setText(bookWorkerInfo.getCustomerInfo().getContact());
        tv_bookingStatus.setText(bookWorkerInfo.getBookstatus());

        databaseReference= FirebaseDatabase.getInstance().getReference("/BookedWorker");


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReference.child(databaseReference.getKey()).child(bookWorkerInfo.getBookstatus()).setValue("Accept");
           /*     bookWorkerInfo.setBookstatus("Accept");
                databaseReference.child(databaseReference.getKey()).setValue(bookWorkerInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ShowWorkerRequestInfo2.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

String key=databaseReference.getKey();
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReference.child(bookWorkerInfo.getBookstatus()).child(databaseReference.getKey()).setValue("Accept");
              /*  bookWorkerInfo.setBookstatus("Reject");
                databaseReference.child(databaseReference.getKey()).setValue(bookWorkerInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ShowWorkerRequestInfo2.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }
}