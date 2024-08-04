package com.example.tamir_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Adapters.Adapter_mytasks;
import com.example.tamir_project.Models.Model_mytasks;
import com.example.tamir_project.databinding.ActivityMytaskBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mytask extends AppCompatActivity {
RecyclerView recyclerView;
Adapter_mytasks adapter;
LinearLayout progressbar;
    TextView toolbar_title;
ActivityMytaskBinding binding;
String URL = Apis_url.BASE_URL+Apis_url.GET_USER_TASKS;
    ImageView back_btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMytaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
getSupportActionBar().hide();
    recyclerView=findViewById(R.id.recyc_mytasks);
        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);
progressbar=findViewById(R.id.sample_progress_layout);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });
        toolbar_title.setText("My tasks");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);


        ArrayList<Model_mytasks> list =new ArrayList<>();


        StringRequest request =new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

if (!response.equals("not found")){

    try {
        JSONArray array =new JSONArray(response);
        for (int i = 0;i< array.length();i++) {
            JSONObject object = array.getJSONObject(i);

            String title = object.getString("task_title");
            String accepted_by = object.getString("accepted_by");
            String accepted_by_worker_id = object.getString("accepted_by_worker_id");

            String budget= object.getString("task_budget");
            String location =object.getString("user_location");
            String post_time =object.getString("post_time");
            String task_status =object.getString("task_status");
            String completetion_time =object.getString("completition_time");
            String payment_status =object.getString("payment_status");




            list.add(new Model_mytasks(post_time,"Accepted by: ",accepted_by,"Budget ",budget+" PKR","Location",title,task_status,completetion_time,payment_status,location,accepted_by_worker_id));
            progressbar.setVisibility(View.GONE);
            adapter = new Adapter_mytasks(list,Mytask.this);

            recyclerView.setAdapter(adapter);


        }
    } catch (JSONException e) {
        progressbar.setVisibility(View.GONE);
        throw new RuntimeException(e);
    }

}
else {
   binding.emptyTaskText.setVisibility(View.VISIBLE);
}


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Mytask.this, "connection error..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map =new HashMap<String,String>();
                SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);

                map.put("id",preferences.getInt("id",0)+"");

                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}