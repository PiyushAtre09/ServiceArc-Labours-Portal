package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourarportal.Pojo.CustomerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class CustomerLogin extends AppCompatActivity {
    EditText et_contact,et_password;
    Button btn_login;
    TextView tv_reg;

    String Pattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    int flg = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        et_contact=findViewById(R.id.et_contact);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
        tv_reg=findViewById(R.id.tv_reg);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**  if (edit_contact.getText().toString().equals("1234567890")&&edit_pass.getText().toString().equals("user@1234"))
                 {
                 Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_LONG).show();
                 Intent i = new Intent(Login.this, NextActivity.class);
                 startActivity(i);
                 }
                 else {
                 Toast.makeText(Login.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();
                 }**/

                //Contact Number Validation
                if (et_contact.getText().toString().isEmpty()) {
                    et_contact.setError("Enter Contact Number");
                    return;
                }
                if (et_password.getText().toString().isEmpty()) {
                    et_password.setError("Enter Password");
                    return;
                }

                databaseReference = FirebaseDatabase.getInstance().getReference("/Customer");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int flg=0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            CustomerInfo customerInfo = data.getValue(CustomerInfo.class);

                            String contact = customerInfo.getContact();
                            String password = customerInfo.getPassword();

                            if (contact.equals(et_contact.getText().toString()) && et_contact.getText().toString().trim().matches(Pattern)) {
                                if (password.equals(et_password.getText().toString())) {
                                    flg=1;
                                    SharedPreferences.Editor prefsEditor = getSharedPreferences("LabourarPortal", MODE_PRIVATE).edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(customerInfo);
                                    prefsEditor.putString("customer", json);
                                    prefsEditor.commit();

                                    Toast.makeText(CustomerLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CustomerLogin.this, Customer_Dashboard.class);
                                    startActivity(intent);
                                    databaseReference.removeEventListener(this);

                                }
                                else {
                                    Toast.makeText(CustomerLogin.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if(flg==0)
                        {
                            Toast.makeText(CustomerLogin.this, "Login failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerLogin.this,Customer_reg.class);
                startActivity(i);

            }

        });

    }
}