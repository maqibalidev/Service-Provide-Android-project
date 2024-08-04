package com.example.tamir_project;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class CustomProgressDialog {

    Context context;
    Dialog dialog;




   public CustomProgressDialog(Context context){
        this.context=context;

    }

    public void showDialog(String title,String subtitle){

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.sample_progress_dialog);
        TextView textView = dialog.findViewById(R.id.custom_dialog_titile);
        TextView textView2 = dialog.findViewById(R.id.custom_dialog_subtitle);
dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

textView.setText(title);
        textView2.setText(subtitle);
dialog.setCancelable(false);
        dialog.create();

dialog.show();

    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}

