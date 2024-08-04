package com.example.tamir_project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.core.text.HtmlKt;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamir_project.Models.Model_notification;
import com.example.tamir_project.R;
import com.google.android.material.tabs.TabLayout;

import org.xml.sax.Parser;

import java.util.ArrayList;

public class Adapter_notification extends RecyclerView.Adapter<Adapter_notification.viewHodler>{

    ArrayList<Model_notification> list;
    Context context;

    public Adapter_notification(ArrayList<Model_notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.sample_notification,parent,false);
        return new viewHodler(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHodler holder, int position) {
Model_notification model=list.get(position);



      holder.noti_txt.setText(Html.fromHtml(model.getNotification_text(),Html.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHodler extends RecyclerView.ViewHolder {

        TextView noti_txt;
        public viewHodler(@NonNull View itemView) {
            super(itemView);

            noti_txt=itemView.findViewById(R.id.notification_txt);
        }
    }
}
