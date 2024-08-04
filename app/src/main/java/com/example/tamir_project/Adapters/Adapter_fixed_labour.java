package com.example.tamir_project.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tamir_project.Models.Model_fixedWorker;
import com.example.tamir_project.R;
import com.example.tamir_project.Worker_profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_fixed_labour extends RecyclerView.Adapter<Adapter_fixed_labour.viewHolder>{
ArrayList<Model_fixedWorker> list;
Context context;

    public Adapter_fixed_labour(ArrayList<Model_fixedWorker> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_fixed_labour,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
Model_fixedWorker model =list.get(position);

        Picasso.get().load(model.getImage()).placeholder(R.drawable.baseline_person_24).into(holder.image);
holder.star_1.setImageResource(model.getStar_1());
holder.star_2.setImageResource(model.getStar_2());
holder.star_3.setImageResource(model.getStar_3());
holder.star_4.setImageResource(model.getStar_4());
holder.star_5.setImageResource(model.getStar_5());

holder.profession.setPaintFlags(holder.profession.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

holder.name.setText(model.getName());
holder.disc.setText(model.getDisc());
holder.profession.setText(model.getProf());
holder.option.setText("Viewprofile");
holder.reviews_count.setText(model.getReviews_count());
holder.option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(context, Worker_profile.class);
        intent.putExtra("id",model.getId());

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
        ImageView star_1,star_2,star_3,star_4,star_5;
        TextView name,disc,reviews_count,option,profession;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.fixed_worker_profile);
            name=itemView.findViewById(R.id.fixed_worker_name);
            disc=itemView.findViewById(R.id.fixed_worker_disc);
            profession=itemView.findViewById(R.id.fixed_worker_prof);
            star_1=itemView.findViewById(R.id.fixed_worker_star_1);
            star_2=itemView.findViewById(R.id.fixed_worker_star_2);
            star_3=itemView.findViewById(R.id.fixed_worker_star_3);
            star_4=itemView.findViewById(R.id.fixed_worker_star_4);
            star_5=itemView.findViewById(R.id.fixed_worker_star_5);
reviews_count=itemView.findViewById(R.id.fixed_worker_reviews_count);
option=itemView.findViewById(R.id.fixed_worker_option);
        }
    }


}
