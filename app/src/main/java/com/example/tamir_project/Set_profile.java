package com.example.tamir_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tamir_project.databinding.ActivitySetProfileBinding;

public class Set_profile extends AppCompatActivity {
ActivitySetProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


    binding.setProfContinueBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (binding.setProfPhone.getText().toString().isEmpty()){
                binding.setProfPhone.setError("please enter name");
            }
            else {


                Intent intent =new Intent(Set_profile.this,Varification.class);

                intent.putExtra("number",binding.setProfPhone.getText().toString());
                intent.putExtra("check",2);
                startActivity(intent);



            }


        }
    });



    }
}