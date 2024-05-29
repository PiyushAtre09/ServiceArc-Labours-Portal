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

import com.example.labourarportal.Pojo.ContractorInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ContractorLogin extends AppCompatActivity {
    EditText cont_contact,cont_password;
    Button btn_login;
    TextView tv_reg;

    String Pattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    int flg = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_login);

        cont_contact=findViewById(R.id.cont_contact);
        cont_password=findViewById(R.id.cont_password);
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
                if (cont_contact.getText().toString().isEmpty()) {
                    cont_contact.setError("Enter Contact Number");
                    return;
                }
                if (cont_password.getText().toString().isEmpty()) {
                    cont_password.setError("Enter Password");
                    return;
                }

                databaseReference = FirebaseDatabase.getInstance().getReference("/Contractor");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            flg = 0;
                            ContractorInfo contractorInfo = data.getValue(ContractorInfo.class);

                            assert contractorInfo != null;
                            String contact = contractorInfo.getContact();
                            String password = contractorInfo.getPassword();

                            SharedPreferences.Editor prefsEditor = getSharedPreferences("LabourPortal", MODE_PRIVATE).edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(contractorInfo);
                            prefsEditor.putString("contractor", json);
                            prefsEditor.commit();
                            databaseReference.removeEventListener(this);

                            if (contact.equals(cont_contact.getText().toString()) && cont_contact.getText().toString().trim().matches(Pattern)) {
                                if (password.equals(cont_password.getText().toString())) {
                                    Toast.makeText(ContractorLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ContractorLogin.this, Contractor_Dashboard.class);
                                    startActivity(intent);
                                }
                                else {

                                    Toast.makeText(ContractorLogin.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                                }
                            }
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
                Intent intent1=new Intent(ContractorLogin.this,Contractor_reg.class);
                startActivity(intent1);
            }
        });
    }
}