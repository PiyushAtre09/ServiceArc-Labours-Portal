package com.example.labourarportal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.labourarportal.Pojo.BookContractorInfo;
import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ShowContractorRequestListInfo2 extends AppCompatActivity {
    TextView tv_custName,tv_custAddress,tv_custContact,tv_bookingStatus;
    Button btn_accept,btn_reject;
    CustomerInfo customerInfo;
    ContractorInfo contractorInfo;
    BookContractorInfo bookContractorInfo;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contractor_request_list_info2);

        tv_custName=findViewById(R.id.tv_custName);
        tv_custAddress=findViewById(R.id.tv_custAddress);
        tv_custContact=findViewById(R.id.tv_custContact);
        tv_bookingStatus=findViewById(R.id.tv_bookingStatus);

        SharedPreferences sh=this.getSharedPreferences("LabourarPortal",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sh.getString("bookcontractorinfo","");
        if (!json.equals("")) {
            bookContractorInfo = gson.fromJson(json, BookContractorInfo.class);
        }

        tv_custName.setText(bookContractorInfo.getCustomerInfo().getName());
        tv_custAddress.setText(bookContractorInfo.getCustomerInfo().getAddress());
        tv_custContact.setText(bookContractorInfo.getCustomerInfo().getContact());
        tv_bookingStatus.setText(bookContractorInfo.getBookstatus());
    }
}