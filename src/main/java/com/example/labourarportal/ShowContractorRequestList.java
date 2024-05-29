package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.labourarportal.Adapter.ContractorRequestAdapter;
import com.example.labourarportal.Adapter.WorkerRequestAdapter;
import com.example.labourarportal.Pojo.BookContractorInfo;
import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowContractorRequestList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BookContractorInfo bookContractorInfo;
    ListView listView;
    ContractorRequestAdapter adapter;
    ArrayList<BookContractorInfo> slist;
    ContractorInfo contractorInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractor_request_list);

        SharedPreferences sh=this.getSharedPreferences("LabourPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("contractor","");
        if (!json.equals("")) {
            contractorInfo = gson.fromJson(json, ContractorInfo.class);
        }

        databaseReference = firebaseDatabase.getInstance().getReference("/BookedContractor");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
            slist=new ArrayList<>();

            for (DataSnapshot dataSnapshot:snapshot.getChildren())
            {
                bookContractorInfo=dataSnapshot.getValue(BookContractorInfo.class);
                if (bookContractorInfo.getContact().equals(contractorInfo.getContact()))
                {
                    slist.add(bookContractorInfo);
                }
            }
               adapter=new ContractorRequestAdapter(ShowContractorRequestList.this, slist);
               listView=findViewById(R.id.lv_viewRequest);
               listView.setAdapter(adapter);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}