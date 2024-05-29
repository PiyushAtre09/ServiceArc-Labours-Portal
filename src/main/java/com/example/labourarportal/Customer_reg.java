package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Customer_reg extends AppCompatActivity {
    EditText edit_name, edit_contact, edit_address, edit_pass;
    Button btn_reg;
    boolean isAllFieldsChecked = false;
    public DatabaseReference databaseReference;
    String Pattern="[0-9]{10}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reg);

        edit_name = findViewById(R.id.edit_name);
        edit_contact = findViewById(R.id.edit_contact);
        edit_address = findViewById(R.id.edit_address);
        edit_pass = findViewById(R.id.edit_pass);
        btn_reg = findViewById(R.id.btn_reg);

        // DatabaseReference fb=new DatabaseReference(FBConfig.fburl+"/Student");
        databaseReference= FirebaseDatabase.getInstance().getReference("/Customer");

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReference.child("student").push().setValue(userPojo);

                if(edit_name.getText().toString().isEmpty()){
                    edit_name.setError("Enter Name");
                    return;
                }

                if( edit_contact.getText().toString().isEmpty()){
                    edit_contact.setError("Enter Contact Number");
                    return;
                }

                if(!edit_contact.getText().toString().trim().matches(Pattern))
                {
                    edit_contact.setError("Contact not valid");
                    return;
                }

                if(edit_address.getText().toString().isEmpty()){
                    edit_address.setError("Enter Address");
                    return;
                }
                if(edit_pass.getText().toString().isEmpty()){
                    edit_pass.setError("Enter Password");
                    return;
                }

                CustomerInfo customerInfo=new CustomerInfo();

                customerInfo.setName(edit_name.getText().toString());
                //userInfo.setEmail(Email.getText().toString());
                customerInfo.setContact(edit_contact.getText().toString());
                customerInfo.setAddress(edit_address.getText().toString());
                customerInfo.setPassword(edit_pass.getText().toString());
                customerInfo.setLatitude("0.0");
                customerInfo.setLongitude("0.0");
                
                SharedPreferences prefs = Customer_reg.this.getSharedPreferences("Labourar Portal", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = prefs.getString("customer", "");
                CustomerInfo customerInfo1 = gson.fromJson(json, CustomerInfo.class);
               // Log.i("#customer:",customerInfo1.getContact());

                databaseReference.child(customerInfo.getContact()).setValue(customerInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(Customer_reg.this, "Register", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Customer_reg.this,CustomerLogin.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

}