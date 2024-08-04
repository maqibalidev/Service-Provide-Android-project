package com.example.tamir_project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamir_project.Models.Model_top_service_provide;
import com.example.tamir_project.Models.Models_nearby_service;
import com.example.tamir_project.R;
import com.example.tamir_project.Worker_profile;

import java.util.ArrayList;

public class Adapter_nearby_service extends RecyclerView.Adapter<Adapter_nearby_service.viewHolder> {

    ArrayList<Models_nearby_service> list;
    Context context;

    public Adapter_nearby_service(ArrayList<Models_nearby_service> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_service_nearby,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

       Models_nearby_service model =list.get(position);

        holder.offerd_by_txt.setText(model.getOfferd_by_txt());
        holder.name.setText(model.getName());
        holder.name.setPaintFlags(holder.name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        holder.work_disc.setText(model.getWork_disc());
        holder.date.setText(model.getDate());
        holder.price.setText(model.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Worker_profile.class);

                intent.putExtra("id",model.getId());


                context.startActivity(intent);

                ((Activity)context).overridePendingTransition(R.anim.slide_left_in,R.anim.right_slide_out);

            }


        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,work_disc,offerd_by_txt,date,price,location;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.service_nearby_name);
            work_disc=itemView.findViewById(R.id.work_disc);
            offerd_by_txt=itemView.findViewById(R.id.offered_by);
            date=itemView.findViewById(R.id.service_nearby_date);
            price=itemView.findViewById(R.id.service_nearby_price);
            location=itemView.findViewById(R.id.service_nearby_location);



        }
    }
}
