package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.labourarportal.Adapter.WorkerAdapter;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowWorker extends AppCompatActivity {

    ListView listView;
    WorkerAdapter adapter;
    ArrayList<WorkerInfo> slist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    WorkerInfo workerInfo;
    SearchView searchView;
    CustomerInfo customerInfo;
    String searchSkills;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_worker);

        searchView= findViewById(R.id.search_workers);
       /* searchSkills=searchView.getQuery().toString();*/


        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("worker","");
        if (!json.equals("")) {
            workerInfo = gson.fromJson(json, WorkerInfo.class);
        }

        databaseReference = firebaseDatabase.getInstance().getReference("/Worker");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slist=new ArrayList<>();
                ArrayList<String> keylist=new ArrayList<String>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    workerInfo = data.getValue(WorkerInfo.class);
                        slist.add(workerInfo);
                }
                adapter=new WorkerAdapter(ShowWorker.this, slist);
                listView=findViewById(R.id.listview);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}