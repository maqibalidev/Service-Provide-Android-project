package com.example.tamir_project.Adapters;

import android.app.Activity;
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

import com.example.tamir_project.Models.Model_top_service_provide;
import com.example.tamir_project.R;
import com.example.tamir_project.Worker_profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_top_service_provider  extends RecyclerView.Adapter<Adapter_top_service_provider.viewHolder>{
ArrayList<Model_top_service_provide> list;
Context context;

    public Adapter_top_service_provider(ArrayList<Model_top_service_provide> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_top_service_provides,parent,false);



        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
Model_top_service_provide model=list.get(position);


Picasso.get().load(model.getImage()).placeholder(R.drawable.baseline_person_24).into(holder.image);
holder.name.setText(model.getName());
holder.profession.setText(model.getProfession());
holder.profession.setPaintFlags(holder.profession.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

holder.location.setText(model.getLocation());
if (model.getVal()>=20 && model.getVal() <=30){
    holder.star_1.setImageResource(R.drawable.baseline_star_24);
    holder.star_2.setImageResource(R.drawable.baseline_star_24);
    holder.star_3.setImageResource(R.drawable.baseline_star_border_24);
    holder.star_4.setImageResource(R.drawable.baseline_star_border_24);
    holder.star_5.setImageResource(R.drawable.baseline_star_border_24);

} else if (model.getVal()>=30 && model.getVal()<=50) {
    holder.star_1.setImageResource(R.drawable.baseline_star_24);
    holder.star_2.setImageResource(R.drawable.baseline_star_24);
    holder.star_3.setImageResource(R.drawable.baseline_star_24);
    holder.star_4.setImageResource(R.drawable.baseline_star_border_24);
    holder.star_5.setImageResource(R.drawable.baseline_star_border_24);
} else if (model.getVal()>=50 && model.getVal()<=100) {
    holder.star_1.setImageResource(R.drawable.baseline_star_24);
    holder.star_2.setImageResource(R.drawable.baseline_star_24);
    holder.star_3.setImageResource(R.drawable.baseline_star_24);
    holder.star_4.setImageResource(R.drawable.baseline_star_24);
    holder.star_5.setImageResource(R.drawable.baseline_star_border_24);
}
else if (model.getVal()>=100) {
    holder.star_1.setImageResource(R.drawable.baseline_star_24);
    holder.star_2.setImageResource(R.drawable.baseline_star_24);
    holder.star_3.setImageResource(R.drawable.baseline_star_24);
    holder.star_4.setImageResource(R.drawable.baseline_star_24);
    holder.star_5.setImageResource(R.drawable.baseline_star_24);
}

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
        return 8;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        ImageView star_1,star_2,star_3,star_4,star_5;
        TextView name,location,profession;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.top_service_provider_image);
            name=itemView.findViewById(R.id.top_service_provider_name);
            location=itemView.findViewById(R.id.top_service_provider_location);
            profession=itemView.findViewById(R.id.top_service_provider_profession);
            star_1=itemView.findViewById(R.id.star_1);
            star_2=itemView.findViewById(R.id.star_2);
            star_3=itemView.findViewById(R.id.star_3);
            star_4=itemView.findViewById(R.id.star_4);
            star_5=itemView.findViewById(R.id.star_5);



        }
    }

}
