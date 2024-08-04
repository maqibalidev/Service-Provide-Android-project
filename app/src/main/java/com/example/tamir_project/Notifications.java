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
import com.example.tamir_project.Adapters.Adapter_fixed_labour;
import com.example.tamir_project.Adapters.Adapter_notification;
import com.example.tamir_project.Models.Model_fixedWorker;
import com.example.tamir_project.Models.Model_notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notifications extends AppCompatActivity {
RecyclerView recyclerView;
    TextView toolbar_title;
String URL=Apis_url.BASE_URL+Apis_url.NOTIFICATIONS;
    ImageView back_btn;
    LinearLayout progressLayout;
    LinearLayout empty_data;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    recyclerView=findViewById(R.id.recyc_noti);
    getSupportActionBar().hide();

        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);
progressLayout=findViewById(R.id.sample_progress_layout);
empty_data=findViewById(R.id.empty_noti_text);
        toolbar_title.setText("Notifications");




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();

            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Model_notification> list=new ArrayList<>();

        try {
            StringRequest request2 = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(!response.equals("not found")){
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = array.length()-1; i >0; i--) {

                                JSONObject object = array.getJSONObject(i);

                                String data = object.getString("data");

                                String time = object.getString("time");
                                String sender_id = object.getString("sender_id");


                                list.add(new Model_notification(data));
progressLayout.setVisibility(View.GONE);
                                Adapter_notification adapter =new Adapter_notification(list,Notifications.this);

                                recyclerView.setAdapter(adapter);


                            }


                        } catch (JSONException e) {

                            Toast.makeText(Notifications.this, e.toString(), Toast.LENGTH_SHORT).show();
                            progressLayout.setVisibility(View.GONE);
                        }

                    }
                    else{
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(Notifications.this, "conenction error..", Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
empty_data.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);
                }


            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);

                    Map<String,String> map2 =new HashMap<String,String>();

                    map2.put("reciever_id",preferences.getInt("id",0)+"");

                    return map2;
                }
            };


            RequestQueue queue2 = Volley.newRequestQueue(Notifications.this);
            queue2.add(request2);

        }
        catch (Exception e){
            Toast.makeText(Notifications.this, "connection error..", Toast.LENGTH_SHORT).show();
        }







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}