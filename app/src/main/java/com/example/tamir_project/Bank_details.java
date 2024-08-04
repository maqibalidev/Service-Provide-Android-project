package com.example.tamir_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bank_details extends AppCompatActivity {
    TextView toolbar_title,debit_card,jazz_card,easypaisa_card;

    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);

        debit_card=findViewById(R.id.debit_card);
        jazz_card=findViewById(R.id.jazz_card);
        easypaisa_card=findViewById(R.id.easypaisa_card);

getSupportActionBar().hide();

        toolbar_title.setText("Bank details");
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });


        debit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bank_details.this,Bank_details_2.class));
            }
        });
       jazz_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bank_details.this,Bank_details_2.class));
            }
        });
        easypaisa_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bank_details.this,Bank_details_2.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}