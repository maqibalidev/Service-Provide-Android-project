package com.example.tamir_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Terms_and_conditions extends AppCompatActivity {
    TextView toolbar_title;

    ImageView back_btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        getSupportActionBar().hide();


        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);



        toolbar_title.setText("Terms and Conditions");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

