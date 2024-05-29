package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.labourarportal.Pojo.BookContractorInfo;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_Contractor_Info2 extends AppCompatActivity {
    CardView cardView;
    CircleImageView img_profile;
    TextView tv_name,tv_address,tv_contatct,tv_skill,tv_status,tv_visitCharges;
    ContractorInfo contractorInfo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button btn_bookContractor;
    CustomerInfo customerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractor_info2);

        cardView=findViewById(R.id.card_workers);
        img_profile=findViewById(R.id.img_profile);
        tv_name=findViewById(R.id.cont_name);
        tv_address=findViewById(R.id.cont_chagres);
        tv_contatct=findViewById(R.id.cont_contact);
        tv_skill=findViewById(R.id.cont_skill);
        tv_status=findViewById(R.id.cont_status);
        tv_visitCharges=findViewById(R.id.cont_chagres);
        btn_bookContractor=findViewById(R.id.btn_bookContractor);

        SharedPreferences sh=this.getSharedPreferences("LabourPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("contractor","");
        if (!json.equals("")) {
            contractorInfo = gson.fromJson(json, ContractorInfo.class);
        }

        SharedPreferences sh1=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson1=new Gson();
        String json1=sh1.getString("customer","");
        if (!json1.equals("")) {
            customerInfo = gson1.fromJson(json1, CustomerInfo.class);
        }


        databaseReference = firebaseDatabase.getInstance().getReference("/Contractor");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(Show_Contractor_Info2.this).load(contractorInfo.getUrl()).into(img_profile);
                tv_name.setText(contractorInfo.getName());
                tv_address.setText(contractorInfo.getAddress());
                tv_contatct.setText(contractorInfo.getContact());
                tv_status.setText(contractorInfo.getStatus());
                tv_skill.setText(contractorInfo.getContractor_skills());
                tv_visitCharges.setText(contractorInfo.getVisitingCharges());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       btn_bookContractor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               databaseReference = firebaseDatabase.getInstance().getReference("/BookedContractor");
               BookContractorInfo bookContractorInfo=new BookContractorInfo();

               bookContractorInfo.setName(tv_name.getText().toString());
               bookContractorInfo.setAddress(tv_address.getText().toString());
               bookContractorInfo.setContact(tv_contatct.getText().toString());
               bookContractorInfo.setSkill(tv_skill.getText().toString());
               bookContractorInfo.setCharges(tv_visitCharges.getText().toString());
               bookContractorInfo.setStatus(tv_status.getText().toString());
               bookContractorInfo.setBookstatus("Pending");
               bookContractorInfo.setUrl(contractorInfo.getUrl());
               bookContractorInfo.setCustomerInfo(customerInfo);

               databaseReference.push().setValue(bookContractorInfo, new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                       Toast.makeText(Show_Contractor_Info2.this, "Contractor Booked", Toast.LENGTH_SHORT).show();

                       Intent intent=new Intent(Show_Contractor_Info2.this,ShowContractor.class);
                       startActivity(intent);
                   }
               });
           }
       });

    }
}