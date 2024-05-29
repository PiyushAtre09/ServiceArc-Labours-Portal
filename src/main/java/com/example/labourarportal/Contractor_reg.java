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

public class Contractor_reg extends AppCompatActivity {
    EditText cont_name,cont_contact,cont_address,cont_password,visit_charges;
    Button btn_reg,btn_addProfile;
    boolean isAllFieldsChecked = false;
    public DatabaseReference databaseReference;
    String Pattern="[0-9]{10}";
    AutoCompleteTextView contracter_skill;
    String selectedSkill;
    CircleImageView img_Profile;
    int SELECT_IMAGE_CODE = 1;
    Uri uri;
    StorageReference storageReference;
    FirebaseStorage storage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_reg);

        cont_name=findViewById(R.id.cont_name);
        cont_contact=findViewById(R.id.cont_contact);
        cont_address=findViewById(R.id.cont_address);
        cont_password=findViewById(R.id.cont_password);
        btn_reg=findViewById(R.id.btn_reg);
        contracter_skill=findViewById(R.id.contracter_skill);
        visit_charges=findViewById(R.id.cont_charges);
        btn_addProfile=findViewById(R.id.btn_profile);
        img_Profile=findViewById(R.id.img_profile);


        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Contractor_reg.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Skills));
        myadapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        contracter_skill.setAdapter(myadapter);


        // DatabaseReference fb=new DatabaseReference(FBConfig.fburl+"/Student");
        databaseReference= FirebaseDatabase.getInstance().getReference("/Contractor");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        btn_addProfile.setOnClickListener(new View.OnClickListener() {
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

                if(cont_name.getText().toString().isEmpty()){
                    cont_name.setError("Enter Name");
                    return;
                }
                if( cont_contact.getText().toString().isEmpty()){
                    cont_contact.setError("Enter Contact Number");
                    return;
                }
                if(!cont_contact.getText().toString().trim().matches(Pattern))
                {
                    cont_contact.setError("Contact not valid");
                    return;
                }
                if(cont_address.getText().toString().isEmpty()){
                    cont_address.setError("Enter Address");
                    return;
                }

                if(contracter_skill.getText().toString().equalsIgnoreCase("Choose Your Skill") || contracter_skill.getText().toString().equalsIgnoreCase("") )
                {
                    Toast.makeText(Contractor_reg.this, "Please Choose Your Skill", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(visit_charges.getText().toString().isEmpty())
                {
                    visit_charges.setError("Enter Your Visit Charges");
                }
                if(cont_password.getText().toString().isEmpty()){
                    cont_password.setError("Enter Password");
                    return;
                }

                if(uri==null)
                {
                    Toast.makeText(Contractor_reg.this, "Add Your Profile:"+uri, Toast.LENGTH_SHORT).show();
                    return;
                }
                StorageReference storageReference1=storageReference.child("file/image1.jpg");
                Toast.makeText(Contractor_reg.this, "uploading..."+uri, Toast.LENGTH_SHORT).show();

                storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Contractor_reg.this, "success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (Contractor_reg.this,ContractorLogin.class);
                        startActivity(intent);

                        Task<Uri> uri1 = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri1.isComplete());
                        Uri url = uri1.getResult();

                        ContractorInfo contractorInfo=new ContractorInfo();
                        contractorInfo.setName(cont_name.getText().toString());
                        contractorInfo.setContact(cont_contact.getText().toString());
                        contractorInfo.setAddress(cont_address.getText().toString());
                        contractorInfo.setContractor_skills(contracter_skill.getText().toString());
                        contractorInfo.setVisitingCharges(visit_charges.getText().toString());
                        contractorInfo.setPassword(cont_password.getText().toString());
                        contractorInfo.setStatus("Busy");
                        contractorInfo.setLatitude("0,0");
                        contractorInfo.setLongitude("0,0");
                        contractorInfo.setUrl(url.toString());


                        databaseReference.child(contractorInfo.getContact()).setValue(contractorInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(Contractor_reg.this, "Register", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Contractor_reg.this,ContractorLogin.class);
                        startActivity(intent);
                    }
                });

                        uri=null;
                        img_Profile.setImageResource(R.drawable.ic_camera);
                        Toast.makeText(Contractor_reg.this, "Image storage info saved", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Contractor_reg.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });


              /*  databaseReference.child(contractorInfo.getContact()).setValue(contractorInfo, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(Contractor_reg.this, "Register", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Contractor_reg.this,ContractorLogin.class);
                        startActivity(intent);
                    }
                });*/
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1)
        {
            uri=data.getData();
            img_Profile.setImageURI(uri);
            btn_addProfile.setText("Done");
        }
    }

}




