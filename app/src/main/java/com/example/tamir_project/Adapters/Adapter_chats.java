package com.example.tamir_project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamir_project.Chatting;
import com.example.tamir_project.Models.Model_Chats;
import com.example.tamir_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_chats extends RecyclerView.Adapter<Adapter_chats.viewHolder>{
ArrayList<Model_Chats> list;
Context context;

    public Adapter_chats(ArrayList<Model_Chats> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_chats,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
Model_Chats model =list.get(position);

        Picasso.get().load(model.getImage()).into(holder.image);

        holder.noti.setImageResource(R.drawable.baseline_error_24);
        if (model.isNoti().equals("1")){
           holder.noti.setVisibility(View.GONE);

        }






        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.disc.setText(model.getDisc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent =new Intent(context, Chatting.class);

                intent.putExtra("name",model.getName());
                intent.putExtra("worker_id",model.getWorker_id());
                intent.putExtra("image",model.getImage());


                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

       CircleImageView image;
       ImageView noti;
       TextView name,disc,time;
       LinearLayout seenLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

        image=itemView.findViewById(R.id.chats_profile);
        noti=itemView.findViewById(R.id.chats_noti);
        name=itemView.findViewById(R.id.chats_name);
        disc=itemView.findViewById(R.id.chats_disc);
        time=itemView.findViewById(R.id.chats_time);
seenLayout=itemView.findViewById(R.id.seen_layout);
        }
    }
}
