package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourarportal.Pojo.ContractorInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class UpdateContractorStatus extends AppCompatActivity {
    Switch available;
    Button btn_update_status;
    TextView tv_status;
    DatabaseReference databaseReference;
    ContractorInfo contractorInfo=new ContractorInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contractor_status);

        available=findViewById(R.id.switch1);
        tv_status=findViewById(R.id.tv_status);
        btn_update_status=findViewById(R.id.btn_update_status);


        databaseReference= FirebaseDatabase.getInstance().getReference("/Contractor");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {

                    ContractorInfo ci = data.getValue(ContractorInfo.class);

                    if ( contractorInfo.getContact().equals(ci.getContact()))
                    {
                        tv_status.setText(contractorInfo.getStatus());

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SharedPreferences sh=this.getSharedPreferences("LabourPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("contractor","");
        if (!json.equals("")) {
            contractorInfo = gson.fromJson(json, ContractorInfo.class);
        }

        btn_update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contractorInfo.setStatus(tv_status.getText().toString());
                databaseReference.child(contractorInfo.getContact()).setValue(contractorInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(UpdateContractorStatus.this, "Status Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}