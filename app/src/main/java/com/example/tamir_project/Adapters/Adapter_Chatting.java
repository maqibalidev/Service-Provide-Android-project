package com.example.tamir_project.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.example.tamir_project.Models.Model_Chatting;
import com.example.tamir_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class Adapter_Chatting extends RecyclerView.Adapter{
ArrayList<Model_Chatting> list;
Context context;
    int SENDER_VIEW_TYPE=1;
    int RECIEVER_VIEW_TYPE=2;
    public Adapter_Chatting(ArrayList<Model_Chatting> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
return new senderViewholder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new recieverViewholder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        Model_Chatting model_chatting = list.get(position);

            SharedPreferences preferences2 = context.getSharedPreferences("user_data",MODE_PRIVATE);
        if (FirebaseAuth.getInstance().getUid().equals(model_chatting.getUser_id())){
            return SENDER_VIEW_TYPE;

        }
        else return RECIEVER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
Model_Chatting model=list.get(position);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("hh:mm a");

if (holder.getClass()==senderViewholder.class){
    ((senderViewholder)holder).sender_msg.setText(model.getMsg());

String date =simpleDateFormat.format(Long.parseLong( model.getTime()));

    ((senderViewholder)holder).sender_time.setText(date);




}
else {
    String date =simpleDateFormat.format(Long.parseLong( model.getTime()));


    ((recieverViewholder)holder).reciever_msg.setText(model.getMsg());
    ((recieverViewholder)holder).reciever_time.setText(date);


}


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class senderViewholder extends RecyclerView.ViewHolder {
        TextView sender_msg,sender_time;

        public senderViewholder(@NonNull View itemView) {
            super(itemView);

            sender_msg=itemView.findViewById(R.id.sender_msg);
            sender_time=itemView.findViewById(R.id.sender_msg_date);

        }
    }
    public class recieverViewholder extends RecyclerView.ViewHolder {
        TextView reciever_msg,reciever_time;
        public recieverViewholder(@NonNull View itemView) {
            super(itemView);

            reciever_msg=itemView.findViewById(R.id.reciever_msg);
            reciever_time=itemView.findViewById(R.id.reciever_msg_date);
        }
    }

}
