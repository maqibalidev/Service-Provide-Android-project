package com.example.tamir_project.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tamir_project.Bank_details;
import com.example.tamir_project.Edit_profile;
import com.example.tamir_project.Mytask;
import com.example.tamir_project.R;
import com.example.tamir_project.Settings;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {



    public Profile() {
        // Required empty public constructor
    }


TextView edit_option,my_task_option,bank_details,settings_opt,prof_name,prof_email;
CircleImageView pro_image;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View root= inflater.inflate(R.layout.fragment_profile, container, false);
        my_task_option=root.findViewById(R.id.my_task_option);
edit_option=root.findViewById(R.id.edit_option);
edit_option.setPaintFlags(edit_option.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
settings_opt=root.findViewById(R.id.settings_opt);
        bank_details=root.findViewById(R.id.bank_details);
pro_image=root.findViewById(R.id.user_prof_img);
prof_name=root.findViewById(R.id.user_prof_name);
        prof_email=root.findViewById(R.id.user_prof_email);


        SharedPreferences preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        Picasso.get().load(preferences.getString("image","")).placeholder(R.drawable.baseline_person_24).into(pro_image);
        prof_name.setText(preferences.getString("name",""));
        prof_email.setText(preferences.getString("email",""));






edit_option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), Edit_profile.class));
    }
});


my_task_option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), Mytask.class));
    }
});



        bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Bank_details.class);
                startActivity(intent);

            }
        });


        settings_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Settings.class));
            }
        });





    return root;
    }
}