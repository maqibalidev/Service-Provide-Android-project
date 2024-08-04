package com.example.tamir_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Set_Password extends AppCompatActivity {
TextView remember_me_setPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        getSupportActionBar().hide();

remember_me_setPass=findViewById(R.id.remember_me_setPass);


        ////////////////////  underling Text  /////////////////////////////
        remember_me_setPass.setPaintFlags(remember_me_setPass.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);



    }
}