package com.example.tamir_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tamir_project.databinding.ActivityTaskDetailsBinding;

public class Task_details extends AppCompatActivity {
    TextView toolbar_title;

    ActivityTaskDetailsBinding binding;

    ImageView back_btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);

        toolbar_title.setText("Task details");


        binding.taskTitle.setText(getIntent().getStringExtra("title"));
        binding.taskTime.setText(getIntent().getStringExtra("posted_on"));
        binding.taskAcceptedBy.setText(getIntent().getStringExtra("accepted_by"));
        binding.taskBufget.setText(getIntent().getStringExtra("budget"));
        binding.taskStatus.setText(getIntent().getStringExtra("task_status"));
        binding.taskLocation.setText(getIntent().getStringExtra("location"));
        binding.completedTime.setText(getIntent().getStringExtra("completed_date"));
        binding.taskPaymetStatus.setText(getIntent().getStringExtra("payment_status"));



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