package com.example.labourarportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Customer_Dashboard extends AppCompatActivity {
    CardView card_ShowWorker,card_ShowContractor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        card_ShowWorker=findViewById(R.id.card_worker);
        card_ShowContractor=findViewById(R.id.card_contractor);

        card_ShowWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customer_Dashboard.this,ShowWorker.class);
                startActivity(intent);
            }
        });

        card_ShowContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customer_Dashboard.this,ShowContractor.class);
                startActivity(intent);
            }
        });
    }
}