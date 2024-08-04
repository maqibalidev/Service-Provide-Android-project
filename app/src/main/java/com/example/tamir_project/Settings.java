package com.example.tamir_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView toolbar_title,contact_us_option,notification_option,language_option;

    ImageView back_btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();
        notification_option=findViewById(R.id.notification_option);
        contact_us_option=findViewById(R.id.contact_us_option);
        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);
        language_option=findViewById(R.id.language_option);
        toolbar_title.setText("Settings");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }});

        contact_us_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,Contact_us.class));
            }
        });

        notification_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,Notifications.class));
            }
        });



        language_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         Dialog dialog=  new Dialog(Settings.this);
//                Button eng=dialog.findViewById(R.id.opt_english);
//                Button urdu=dialog.findViewById(R.id.opt_urdu);

                dialog.setContentView(R.layout.select_language_dialogbox);

                dialog.show();

//                eng.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//
//                    }
//                });





            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}