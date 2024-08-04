package com.example.tamir_project.ui.add_Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Apis_url;
import com.example.tamir_project.CustomProgressDialog;
import com.example.tamir_project.databinding.FragmentTaskBinding;

import org.jetbrains.annotations.TestOnly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TaskFragment extends Fragment {

    private FragmentTaskBinding binding;
   CustomProgressDialog dialog;
String URL= Apis_url.BASE_URL+Apis_url.UPLOAD_USER_TASKS;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dialog=new CustomProgressDialog(getContext());
        binding.taskUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.taskTitle.getText().toString().isEmpty()){
                    binding.taskTitle.setError("please enter task title");
                } else if (binding.taskDisc.getText().toString().isEmpty()) {
                    binding.taskDisc.setError("please enter task discription");
                } else if (binding.taskAdress.getText().toString().isEmpty()) {
                    binding.taskAdress.setError("please enter adress");

                } else if (binding.taskBudget.getText().toString().isEmpty()) {
                    binding.taskBudget.setError("please enter task budget");
                }
                else {
dialog.showDialog("Uploading task","Please wait..");
                    uploadTask();

                }


            }
        });







        return root;
    }

    private void uploadTask() {


        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
dialog.dismissDialog();
Toast.makeText(getContext(), "task uploaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismissDialog();
                    Toast.makeText(getContext(), "failed to upload task", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog();
                Toast.makeText(getContext(), "connection error.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map = new HashMap<String, String>();

                SharedPreferences preferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);

                Date date =new Date();

                map.put("task_title",binding.taskTitle.getText().toString());
                map.put("task_discription",binding.taskDisc.getText().toString());
                map.put("task_budget",binding.taskBudget.getText().toString());
                map.put("post_time",date.getTime()+"");
                map.put("user_location",binding.taskAdress.getText().toString());

                map.put("accepted_by_worker_id","0");
                map.put("completition_time","---");
                map.put("payment_status","Not defined");
                map.put("user_id",preferences.getInt("id",0)+"");
                map.put("task_status","Not completed");
                map.put("accepted_by","Not accepted ");

                return  map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}