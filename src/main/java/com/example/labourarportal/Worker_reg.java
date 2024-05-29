package com.example.labourarportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class Worker_reg extends AppCompatActivity {
    EditText worker_name, worker_contact, worker_address,worker_password,visit_charges;
    Button btn_reg, btn_profile;
    boolean isAllFieldsChecked = false;
    public DatabaseReference databaseReference;
    String Pattern="[0-9]{10}";
    AutoCompleteTextView workerSkill;
    String selectedSkill;
    CircleImageView img_profile;
    int SELECT_IMAGE_CODE = 1;
    Uri uri;
    StorageReference storageReference;
    FirebaseStorage storage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_reg);

        worker_name = findViewById(R.id.worker_name);
        worker_contact = findViewById(R.id.worker_contact);
        worker_address = findViewById(R.id.worker_address);
        worker_password = findViewById(R.id.worker_password);
        workerSkill=findViewById(R.id.worker_skill);
        visit_charges=findViewById(R.id.worker_charges);
        btn_reg = findViewById(R.id.btn_reg);
        btn_profile=findViewById(R.id.btn_profile);
        img_profile=findViewById(R.id.img_profile);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Worker_reg.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Skills));
        myadapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        workerSkill.setAdapter(myadapter);

        // DatabaseReference fb=new DatabaseReference(FBConfig.fburl+"/Student");
        databaseReference= FirebaseDatabase.getInstance().getReference("/Worker");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose"),SELECT_IMAGE_CODE);
            }
        });


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //databaseReference.child("student").push().setValue(userPojo);

                if(worker_name.getText().toString().isEmpty()){
                    worker_name.setError("Enter Name");
                    return;
                }
                if( worker_contact.getText().toString().isEmpty()){
                    worker_contact.setError("Enter Contact Number");
                    return;
                }
                if(!worker_contact.getText().toString().trim().matches(Pattern))
                {
                    worker_contact.setError("Contact not valid");
                    return;
                }
                if(worker_address.getText().toString().isEmpty()){
                    worker_address.setError("Enter Password");
                    return;
                }

                if(workerSkill.getText().toString().equalsIgnoreCase("Choose Your Skill") || workerSkill.getText().toString().equalsIgnoreCase("") )
                {
                    Toast.makeText(Worker_reg.this, "Please Choose Your Skill", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(visit_charges.getText().toString().isEmpty())
                {
                    visit_charges.setError("Enter Your Visit Charges");
                }
                if(worker_password.getText().toString().isEmpty()){
                    worker_password.setError("Enter Password");
                    return;
                }


                if(uri==null)
                {
//                    WorkerInfo wi=new WorkerInfo();
//
//                    wi.setUrl("");
//                    wi.setName("image");
//
//                    databaseReference.push().setValue(wi);
                    Toast.makeText(Worker_reg.this, "Add Your Profile:"+uri, Toast.LENGTH_SHORT).show();

                    return;
                }

                StorageReference storageReference1=storageReference.child("file/image.jpg");
                Toast.makeText(Worker_reg.this, "uploading..."+uri, Toast.LENGTH_SHORT).show();

                storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       /* Toast.makeText(Worker_reg.this, "success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (Worker_reg.this,WorkerLogin.class);
                        startActivity(intent);
*/

                        Task<Uri> uri1 = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri1.isComplete());
                        Uri url = uri1.getResult();


                       WorkerInfo workerInfo=new WorkerInfo();

                        workerInfo.setUrl(url.toString());
//                        workerInfo.setProfile("Profile");

                        workerInfo.setName(worker_name.getText().toString());
                        workerInfo.setContact(worker_contact.getText().toString());
                        workerInfo.setAddress(worker_address.getText().toString());
                        workerInfo.setWorker_skill(workerSkill.getText().toString());
                        workerInfo.setVisit_charges(visit_charges.getText().toString());
                        workerInfo.setPassword(worker_password.getText().toString());
                        workerInfo.setLatitude("0.0");
                        workerInfo.setLongitude("0.0");
                        workerInfo.setStatus("Busy");
                        databaseReference.child(workerInfo.getContact()).setValue(workerInfo, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(Worker_reg.this, "Register", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(Worker_reg.this,WorkerLogin.class);
                                startActivity(intent);
                            }
                        });
                        uri=null;
                        img_profile.setImageResource(R.drawable.ic_camera);
                        Toast.makeText(Worker_reg.this, "Image storage info saved", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Worker_reg.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1)
        {
            uri=data.getData();
            img_profile.setImageURI(uri);
            btn_profile.setText("Done");
        }
    }

}