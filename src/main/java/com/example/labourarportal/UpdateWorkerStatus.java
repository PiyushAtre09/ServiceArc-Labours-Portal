package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class UpdateWorkerStatus extends AppCompatActivity {
    Switch available;
    Button btn_update_status;
    TextView tv_status;
    DatabaseReference databaseReference;
    CardView card_showRequest;
    WorkerInfo workerInfo=new WorkerInfo();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_worker_status);

        available=findViewById(R.id.switch1);
        tv_status=findViewById(R.id.tv_status);
        btn_update_status=findViewById(R.id.btn_update_status);

        available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_status.setText("Available");
                } else {

                    tv_status.setText("Busy");
                }
            }
        });


        databaseReference= FirebaseDatabase.getInstance().getReference("/Worker");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {

                    WorkerInfo wi = data.getValue(WorkerInfo.class);

                    if ( workerInfo.getContact().equals(wi.getContact()))
                    {
                        tv_status.setText(workerInfo.getStatus());

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("worker","");
        if (!json.equals("")) {
            workerInfo = gson.fromJson(json, WorkerInfo.class);
        }

        btn_update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workerInfo.setStatus(tv_status.getText().toString());
                databaseReference.child(workerInfo.getContact()).setValue(workerInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(UpdateWorkerStatus.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}