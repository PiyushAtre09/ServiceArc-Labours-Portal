package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.labourarportal.Adapter.ContractorAdapter;
import com.example.labourarportal.Adapter.WorkerAdapter;
import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowContractor extends AppCompatActivity {
    ListView listView;
    ContractorAdapter adapter;
    ArrayList<ContractorInfo> slist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ContractorInfo contractorInfo;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractor);

        searchView=findViewById(R.id.search_workers);

        SharedPreferences sh=this.getSharedPreferences("LabourPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("contractor","");
        if (!json.equals(""))
        {
            contractorInfo = gson.fromJson(json, ContractorInfo.class);
        }

        databaseReference = firebaseDatabase.getInstance().getReference("/Contractor");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slist=new ArrayList<>();
                ArrayList<String> keylist=new ArrayList<String>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    contractorInfo = data.getValue(ContractorInfo.class);
                    slist.add(contractorInfo);

                }
                adapter=new ContractorAdapter(ShowContractor.this, slist);
                listView=findViewById(R.id.listview);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}