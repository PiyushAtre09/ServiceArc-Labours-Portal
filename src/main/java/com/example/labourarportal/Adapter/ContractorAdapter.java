package com.example.labourarportal.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.labourarportal.Pojo.ContractorInfo;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.example.labourarportal.R;
import com.example.labourarportal.ShowWorkerInfo2;
import com.example.labourarportal.Show_Contractor_Info2;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContractorAdapter extends ArrayAdapter<String> {
    private Activity context = null;
    ArrayList<ContractorInfo> slist=new ArrayList<>();

    public ContractorAdapter(@NonNull Activity context, ArrayList<ContractorInfo> slist) {
        super(context, R.layout.activity_show_contractor);
        this.context = context;
        this.slist = slist;


    }
    public int getCount() {
        return slist.size();

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.activity_show_contractor_info, null,true);

        TextView Name = (TextView) rowView.findViewById(R.id.cont_name);
        TextView Skill = (TextView) rowView.findViewById(R.id.cont_skill);
        TextView Charges = (TextView) rowView.findViewById(R.id.cont_chagres);
        CircleImageView imageView=rowView.findViewById(R.id.img_profile);

        ContractorInfo contractorInfo=slist.get(position);

        Name.setText(contractorInfo.getName());
        Skill.setText(contractorInfo.getContractor_skills());
        Charges.setText(contractorInfo.getVisitingCharges());
        Glide.with(context).load(contractorInfo.getUrl()).into(imageView);

        TextView tv_moreInfo=rowView.findViewById(R.id.tv_moreInfo);

        tv_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor prefsEditor = context.getSharedPreferences("LabourPortal", Context.MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(contractorInfo);
                prefsEditor.putString("contractor", json);
                prefsEditor.commit();

                Intent intent = new Intent(context, Show_Contractor_Info2.class);
                context.startActivity(intent);

            }

        });

        return rowView;
    };
}
