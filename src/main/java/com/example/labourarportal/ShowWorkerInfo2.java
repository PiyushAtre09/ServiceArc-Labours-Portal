package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowWorkerInfo2 extends AppCompatActivity {

    CardView cardView;
    CircleImageView img_profile;
    TextView tv_name,tv_address,tv_contatct,tv_skill,tv_status,tv_visitCharges;
    WorkerInfo workerInfo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button btn_bookWorker;
    CustomerInfo customerInfo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_worker_info2);

        cardView=findViewById(R.id.card_workers);
        img_profile=findViewById(R.id.img_profile);
        tv_name=findViewById(R.id.worker_name);
        tv_address=findViewById(R.id.worker_address);
        tv_contatct=findViewById(R.id.worker_contact);
        tv_skill=findViewById(R.id.worker_skill);
        tv_status=findViewById(R.id.worker_status);
        tv_visitCharges=findViewById(R.id.worker_charges);
        btn_bookWorker=findViewById(R.id.btn_bookWorker);

        SharedPreferences sh1=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson1=new Gson();
        String json1=sh1.getString("customer","");
        if (!json1.equals("")) {
            customerInfo = gson1.fromJson(json1, CustomerInfo.class);
        }

        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("worker","");
        if (!json.equals("")) {
            workerInfo = gson.fromJson(json, WorkerInfo.class);
        }


        databaseReference = firebaseDatabase.getInstance().getReference("/Worker");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(ShowWorkerInfo2.this).load(workerInfo.getUrl()).into(img_profile);
                tv_name.setText(workerInfo.getName());
                tv_address.setText(workerInfo.getAddress());
                tv_contatct.setText(workerInfo.getContact());
                tv_status.setText(workerInfo.getStatus());
                tv_skill.setText(workerInfo.getWorker_skill());
                tv_visitCharges.setText(workerInfo.getVisit_charges());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

btn_bookWorker.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        databaseReference = firebaseDatabase.getInstance().getReference("/BookedWorker");
        BookWorkerInfo bookWorkerInfo=new BookWorkerInfo();
        bookWorkerInfo.setName(tv_name.getText().toString());
        bookWorkerInfo.setAddress(tv_address.getText().toString());
        bookWorkerInfo.setContact(tv_contatct.getText().toString());
        bookWorkerInfo.setStatus(tv_status.getText().toString());
        bookWorkerInfo.setSkill(tv_skill.getText().toString());
        bookWorkerInfo.setCharges(tv_visitCharges.getText().toString());
        bookWorkerInfo.setBookstatus("Pending");
        bookWorkerInfo.setCustomerInfo(customerInfo);
        bookWorkerInfo.setProfile(workerInfo.getUrl());

        databaseReference.push().setValue(bookWorkerInfo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ShowWorkerInfo2.this, "Worker Booked", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(ShowWorkerInfo2.this,ShowWorker.class);
                startActivity(intent);
            }
        });

    }
});


    }
}