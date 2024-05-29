package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class WorkerLogin extends AppCompatActivity {
    EditText worker_contact,worker_password;
    Button btn_login;
    TextView tv_reg;
BookWorkerInfo bookWorkerInfo;
    String Pattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    int flg = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        worker_contact=findViewById(R.id.worker_contact);
        worker_password=findViewById(R.id.worker_password);
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
                if (worker_contact.getText().toString().isEmpty()) {
                    worker_contact.setError("Enter Contact Number");
                    return;
                }
                if (worker_password.getText().toString().isEmpty()) {
                    worker_password.setError("Enter Password");
                    return;
                }

                databaseReference = FirebaseDatabase.getInstance().getReference("/Worker");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            flg = 0;
                            WorkerInfo workerInfo = data.getValue(WorkerInfo.class);
                            String contact = workerInfo.getContact();
                            String password = workerInfo.getPassword();


                            databaseReference.removeEventListener(this);

                            if (contact.equals(worker_contact.getText().toString())) {
                                if (password.equals(worker_password.getText().toString())) {
                                    Toast.makeText(WorkerLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor prefsEditor = getSharedPreferences("LabourarPortal", MODE_PRIVATE).edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(workerInfo);
                                    prefsEditor.putString("worker", json);
                                    prefsEditor.commit();

                                    Intent intent = new Intent(WorkerLogin.this, Worker_Dashboard.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    Toast.makeText(WorkerLogin.this, "Invalid Details", Toast.LENGTH_SHORT).show();
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
                Intent intent1=new Intent(WorkerLogin.this,Worker_reg.class);
                startActivity(intent1);
            }
        });
    }
}