package com.example.tamir_project.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamir_project.Models.Model_mytasks;
import com.example.tamir_project.R;
import com.example.tamir_project.Task_details;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_mytasks extends RecyclerView.Adapter<Adapter_mytasks.viewHolder> {
ArrayList<Model_mytasks> list;
Context context;

    public Adapter_mytasks(ArrayList<Model_mytasks> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.sample_mytasks,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
Model_mytasks model =list.get(position);

        holder.work_disc.setText(model.getTitle());
        holder.accepted_by_name.setText(model.getAccepted_by_name());
        holder.accepted_by_name.setPaintFlags(holder.accepted_by_name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        holder.accepted_by_txt.setText(model.getAccepted_by_txt());
        holder.location_txt.setText(model.getLocation_txt());
        holder.location.setText(model.getLocation());
        holder.budget_price.setText(model.getBudget_price());
        holder.budget_txt.setText(model.getBudget_txt());
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd MMM yyy", Locale.getDefault());

        String dates = simpleDateFormat.format(Long.parseLong(model.getDays()));

        holder.days.setText(dates);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(context,Task_details.class);

               intent.putExtra("title",model.getTitle());
               intent.putExtra("posted_on",dates);
               intent.putExtra("accepted_by",model.getAccepted_by_name());
               intent.putExtra("budget",model.getBudget_price());
               intent.putExtra("task_status",model.getStatus());
               intent.putExtra("location",model.getLocation());
               intent.putExtra("completed_date",model.getCompletetion_time());
               intent.putExtra("payment_status",model.getPayemnt_status());


               context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder {
        TextView work_disc,days,accepted_by_txt,accepted_by_name,budget_txt, budget_price,location_txt,location;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            work_disc=itemView.findViewById(R.id.my_tasks_work_disc);
            days=itemView.findViewById(R.id.my_tasks_day);
            accepted_by_txt=itemView.findViewById(R.id.my_tasks_accepted);
            accepted_by_name=itemView.findViewById(R.id.my_tasks_accepted_by_name);
            budget_txt=itemView.findViewById(R.id.my_tasks_budget);
            budget_price=itemView.findViewById(R.id.my_tasks_buudget_price);
            location_txt=itemView.findViewById(R.id.my_tasks_location_txt);
            location=itemView.findViewById(R.id.my_tasks_location);

        }
    }
}
