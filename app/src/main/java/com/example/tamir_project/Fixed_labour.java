package com.example.tamir_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.tamir_project.Adapters.Adapter_top_service_provider;
import com.example.tamir_project.Models.Model_fixedWorker;
import com.example.tamir_project.Models.Model_top_service_provide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fixed_labour extends AppCompatActivity {
RecyclerView recyclerView;
Adapter_fixed_labour adapter;
public static final String FIXED_WORKER_URL=Apis_url.BASE_URL+Apis_url.FIXED_WORKER;
    public static final String WORKER_PROF=Apis_url.BASE_URL+Apis_url.WORKER_PROF;
    public static final String URL=Apis_url.BASE_URL+Apis_url.SEARCH;

    String getTitle;
    LinearLayout progress,emptyText;
    ImageView back_btn;
    TextView toolbar_title;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_labour);
    recyclerView=findViewById(R.id.recyc_fixed_labour);
   progress=findViewById(R.id.sample_progress_layout);
   emptyText=findViewById(R.id.empty_fixelobour_text);
        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getSupportActionBar().hide();
      getTitle=getIntent().getStringExtra("title");
        toolbar_title.setText(getTitle);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });



        ArrayList<Model_fixedWorker> list =new ArrayList<>();


if (getIntent().getIntExtra("check",0)==1){
    try {
        StringRequest request2 = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               if(!response.equals("not found")){
                   try {
                       JSONArray array = new JSONArray(response);
                       for (int i = 0; i < array.length(); i++) {

                           JSONObject object = array.getJSONObject(i);

                           String name = object.getString("worker_name");
                           int id = object.getInt("id");
                           String worker_profession = object.getString("worker_profession");
                           String worker_image = object.getString("worker_image");
                           String city = object.getString("worker_city");
                           String worker_price = object.getString("worker_price");
                           String worker_adress = object.getString("worker_adress");
                           String worker_latitude = object.getString("worker_latitude");
                           String worker_longitude = object.getString("worker_longitude");
                           int worker_rating = Integer.parseInt(object.getString("worker_rating"));
                           String worker_discription = object.getString("worker_discription");
                           String completed_tasks = object.getString("completed_tasks");
                           String completition_rate = object.getString("completition_rate");

                           list.add(new Model_fixedWorker(id,worker_image,R.drawable.baseline_star_24,R.drawable.baseline_star_24,R.drawable.baseline_star_border_24
                                   ,R.drawable.baseline_star_border_24,R.drawable.baseline_star_border_24,name,
                                   worker_discription,worker_profession,"34 Reviews"));


progress.setVisibility(View.GONE);

                           adapter=new Adapter_fixed_labour(list,Fixed_labour.this);

                           recyclerView.setAdapter(adapter);


                       }


                   } catch (JSONException e) {
progress.setVisibility(View.GONE);
                       Toast.makeText(Fixed_labour.this, "Connection error..", Toast.LENGTH_SHORT).show();

                   }

               }
               else{
emptyText.setVisibility(View.VISIBLE);
progress.setVisibility(View.GONE);

               }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.GONE);

                Toast.makeText(Fixed_labour.this,"Conenction error..", Toast.LENGTH_SHORT).show();
            }


        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map2 =new HashMap<String,String>();

                map2.put("search",getIntent().getStringExtra("search"));

                return map2;
            }
        };


        RequestQueue queue2 = Volley.newRequestQueue(Fixed_labour.this);
        queue2.add(request2);

    }
    catch (Exception e){
        Toast.makeText(Fixed_labour.this, "connection error..", Toast.LENGTH_SHORT).show();
    }
}
else{

    if (getTitle.equals("Fixed Labour")){

        try {
            StringRequest request = new StringRequest(Request.Method.GET, FIXED_WORKER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
              if (!response.equals("not found")){
                  try {
                      JSONArray array = new JSONArray(response);
                      for (int i = 0; i < array.length(); i++) {

                          JSONObject object = array.getJSONObject(i);

                          String name = object.getString("worker_name");
                          int id = object.getInt("id");
                          String worker_profession = object.getString("worker_profession");
                          String worker_image = object.getString("worker_image");
                          String city = object.getString("worker_city");
                          String worker_price = object.getString("worker_price");
                          String worker_adress = object.getString("worker_adress");
                          String worker_latitude = object.getString("worker_latitude");
                          String worker_longitude = object.getString("worker_longitude");
                          int worker_rating = Integer.parseInt(object.getString("worker_rating"));
                          String worker_discription = object.getString("worker_discription");
                          String completed_tasks = object.getString("completed_tasks");
                          String completition_rate = object.getString("completition_rate");

                          list.add(new Model_fixedWorker(id,worker_image,R.drawable.baseline_star_24,R.drawable.baseline_star_24,R.drawable.baseline_star_border_24
                                  ,R.drawable.baseline_star_border_24,R.drawable.baseline_star_border_24,name,
                                  worker_discription,worker_profession,"34 Reviews"));

                          progress.setVisibility(View.GONE);


                          adapter=new Adapter_fixed_labour(list,Fixed_labour.this);

                          recyclerView.setAdapter(adapter);

                      }


                  } catch (JSONException e) {
                      progress.setVisibility(View.GONE);
                      Toast.makeText(Fixed_labour.this ,"connection error", Toast.LENGTH_SHORT).show();


                  }

              }
              else {
                  progress.setVisibility(View.GONE);
                  emptyText.setVisibility(View.VISIBLE);
              }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progress.setVisibility(View.GONE);

                    Toast.makeText(Fixed_labour.this, "connection error..", Toast.LENGTH_SHORT).show();
                }


            });


            RequestQueue queue = Volley.newRequestQueue(Fixed_labour.this);
            queue.add(request);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    else {

        try {
            StringRequest request = new StringRequest(Request.Method.POST, WORKER_PROF, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equals("not found")){

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object = array.getJSONObject(i);

                                String name = object.getString("worker_name");
                                int id = object.getInt("id");
                                String worker_profession = object.getString("worker_profession");
                                String worker_image = object.getString("worker_image");
                                String city = object.getString("worker_city");
                                String worker_price = object.getString("worker_price");
                                String worker_adress = object.getString("worker_adress");
                                String worker_latitude = object.getString("worker_latitude");
                                String worker_longitude = object.getString("worker_longitude");
                                int worker_rating = Integer.parseInt(object.getString("worker_rating"));
                                String worker_discription = object.getString("worker_discription");
                                String completed_tasks = object.getString("completed_tasks");
                                String completition_rate = object.getString("completition_rate");

                                list.add(new Model_fixedWorker(id,worker_image,R.drawable.baseline_star_24,R.drawable.baseline_star_24,R.drawable.baseline_star_border_24
                                        ,R.drawable.baseline_star_border_24,R.drawable.baseline_star_border_24,name,
                                        worker_discription,worker_profession,"34 Reviews"));


                                progress.setVisibility(View.GONE);


                                adapter=new Adapter_fixed_labour(list,Fixed_labour.this);

                                recyclerView.setAdapter(adapter);

                            }


                        } catch (JSONException e) {
                            progress.setVisibility(View.GONE);

                            Toast.makeText(Fixed_labour.this ,"connection error", Toast.LENGTH_SHORT).show();


                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        emptyText.setVisibility(View.VISIBLE);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progress.setVisibility(View.GONE);

                    Toast.makeText(Fixed_labour.this, "connection error..", Toast.LENGTH_SHORT).show();
                }


            }){

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("profession",getTitle);

                    return map;
                }
            };


            RequestQueue queue = Volley.newRequestQueue(Fixed_labour.this);
            queue.add(request);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}






    }

    @Override
    public void onBackPressed() {
    super.onBackPressed();
    }
}