package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.labourarportal.Adapter.ContractorRequestAdapter;
import com.example.labourarportal.Adapter.WorkerRequestAdapter;
import com.example.labourarportal.Pojo.BookContractorInfo;
import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowWorkerRequestList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BookWorkerInfo bookWorkerInfo;
    ListView listView;
    WorkerRequestAdapter adapter;
    ArrayList<BookWorkerInfo> slist;
    WorkerInfo workerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_worker_request_list);

        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("worker","");
        if (!json.equals("")) {
            workerInfo = gson.fromJson(json, WorkerInfo.class);
        }

        databaseReference = firebaseDatabase.getInstance().getReference("/BookedWorker");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                slist=new ArrayList<BookWorkerInfo>();

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    bookWorkerInfo=dataSnapshot.getValue(BookWorkerInfo.class);
                    if (bookWorkerInfo.getContact().equals(workerInfo.getContact()))
                    {
                        slist.add(bookWorkerInfo);
                    }
                }
                adapter=new WorkerRequestAdapter(ShowWorkerRequestList.this, slist);
                listView=findViewById(R.id.lv_viewRequest);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}