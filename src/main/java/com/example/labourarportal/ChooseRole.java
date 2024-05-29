package com.example.labourarportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseRole extends AppCompatActivity {
    CardView card_customer,card_contractor,card_worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        card_customer=findViewById(R.id.card_customer);
        card_contractor=findViewById(R.id.card_contractor);
        card_worker=findViewById(R.id.card_worker);

        card_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(ChooseRole.this,CustomerLogin.class);
                startActivity(i1);
            }
        });

        card_contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(ChooseRole.this,ContractorLogin.class);
                startActivity(i1);
            }
        });

        card_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(ChooseRole.this,WorkerLogin.class);
                startActivity(i1);
            }
        });
    }
}