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
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.labourarportal.Pojo.WorkerInfo;
import com.example.labourarportal.R;
import com.example.labourarportal.ShowWorkerInfo2;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("MissingInflatedId")
public class WorkerAdapter extends ArrayAdapter<String> {
    private Activity context = null;
    ArrayList<WorkerInfo> slist=new ArrayList<>();

    public WorkerAdapter(@NonNull Activity context, ArrayList<WorkerInfo> slist) {
        super(context, R.layout.activity_show_worker);
        this.context = context;
        this.slist = slist;

    }

    public int getCount() {
        return slist.size();

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.activity_show_worker_info, null, true);
        CircleImageView img=rowView.findViewById(R.id.img_profile);
        TextView Name = (TextView) rowView.findViewById(R.id.worker_name);
        TextView Skill = (TextView) rowView.findViewById(R.id.worker_skill);
        TextView Charges = (TextView) rowView.findViewById(R.id.worker_charges);
//        TextView Latitude = (TextView) rowView.findViewById(R.id.latitude);
//        TextView Longitude = (TextView) rowView.findViewById(R.id.longitude);


        WorkerInfo workerInfo = slist.get(position);

        Name.setText(workerInfo.getName());
        Skill.setText(workerInfo.getWorker_skill());
        Charges.setText(workerInfo.getVisit_charges());

        Glide.with(context).load(workerInfo.getUrl()).into(img);
//        Latitude.setText(workerInfo.getLatitude());
//        Longitude.setText(workerInfo.getLongitude());
        CardView card1 = rowView.findViewById(R.id.card_workers);
        TextView tv_moreInfo=rowView.findViewById(R.id.tv_moreInfo);

        tv_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor prefsEditor = context.getSharedPreferences("LabourarPortal", Context.MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(workerInfo);
                prefsEditor.putString("worker", json);
                prefsEditor.commit();

                Intent intent = new Intent(context, ShowWorkerInfo2.class);
                context.startActivity(intent);

            }

        });
        return rowView;
    }

}
