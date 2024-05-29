package com.example.labourarportal.Adapter;

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

import com.example.labourarportal.Pojo.BookWorkerInfo;
import com.example.labourarportal.Pojo.CustomerInfo;
import com.example.labourarportal.R;
import com.example.labourarportal.ShowWorkerRequestInfo2;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WorkerRequestAdapter extends ArrayAdapter<String> {
    private Activity context = null;
    ArrayList<BookWorkerInfo> slist=new ArrayList<>();
    CustomerInfo customerInfo;

    public WorkerRequestAdapter(@NonNull Activity context, ArrayList<BookWorkerInfo> slist) {
        super(context, R.layout.activity_show_worker_request_list);
        this.context = context;
        this.slist = slist;
    }

    public int getCount() {
        return slist.size();
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.activity_show_worker_request_list_info, null, true);

        TextView Name = (TextView) rowView.findViewById(R.id.tv_name);
        TextView Status = (TextView) rowView.findViewById(R.id.tv_status);

        BookWorkerInfo bookWorkerInfo = slist.get(position);

        Name.setText(bookWorkerInfo.getCustomerInfo().getName());
        Status.setText(bookWorkerInfo.getBookstatus());

        TextView tv_showRequest=rowView.findViewById(R.id.tv_viewRequest);

        tv_showRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor prefsEditor = context.getSharedPreferences("LabourarPortal", Context.MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(bookWorkerInfo);
                prefsEditor.putString("bookinfo", json);
                prefsEditor.commit();

                Intent intent = new Intent(context, ShowWorkerRequestInfo2.class);
                context.startActivity(intent);

            }

        });
        return rowView;
    }
}
