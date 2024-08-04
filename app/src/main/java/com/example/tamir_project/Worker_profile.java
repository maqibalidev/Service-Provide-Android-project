package com.example.tamir_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Adapters.Adapter_top_service_provider;
import com.example.tamir_project.Models.Model_top_service_provide;
import com.example.tamir_project.databinding.ActivityWorkerProfileBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Worker_profile extends AppCompatActivity {
TextView worker_name,worker_prof,toolbar_title;
ImageView back_btn;
ActivityWorkerProfileBinding binding;
    int worker_id;
Button worker_profile_send_btn;
String proImage="";
int workerId;
String WORKER_DETAILS=Apis_url.BASE_URL+Apis_url.WORKER_DETAILS;
    String WORKER_PORTFOLIO=Apis_url.BASE_URL+Apis_url.WORKER_PORTFOLIO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityWorkerProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        toolbar_title=findViewById(R.id.activity_toolbar_title);
        back_btn=findViewById(R.id.back_btn_for_activity);
getSupportActionBar().hide();


        binding.workerProf.setPaintFlags(binding.workerProf.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
worker_id=getIntent().getIntExtra("id",0);







getWorker_details();








toolbar_title.setText("Worker Profile");

        binding.workerProfileSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(Worker_profile.this,Chatting.class);
               intent.putExtra("name",binding.workerName.getText().toString());
                intent.putExtra("worker_id",worker_id+"");
                intent.putExtra("image",proImage);

                startActivity(intent);
            }
        });


back_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
 onBackPressed();


    }
});


    }

    private void getWorker_details() {


        StringRequest request = new StringRequest(Request.Method.POST, WORKER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              if(!response.equals("error")){

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


                          proImage=worker_image;
                          binding.workerName.setText(name);
                          binding.workerProf.setText(worker_profession);
                          Picasso.get().load(proImage).placeholder(R.drawable.baseline_person_24).into(binding.workerProfilePic);
                          binding.workerDetailsCompletitionRate.setText(completition_rate);
                          binding.workerDetailsProf.setText(worker_profession);
                          binding.workerDetailsAdress.setText(worker_adress);
                          binding.workerDeatailsDisc.setText(worker_discription);
                          binding.workerTaskCompletd.setText(completed_tasks);
                          binding.workerDetailsPrice.setText(worker_price);
                          int ratings = worker_rating;
                          showRating(ratings);
                          showPortfolios( id);
                      }


                  } catch (JSONException e) {
                      throw new RuntimeException(e);
                  }
                  
                  
              }
else{
                  Toast.makeText(Worker_profile.this, "no data found", Toast.LENGTH_SHORT).show();
              }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Worker_profile.this, "connection error..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("id",worker_id+"");


                return  map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Worker_profile.this);
        queue.add(request);





    }

    private void showPortfolios(int id) {


        StringRequest request = new StringRequest(Request.Method.POST, WORKER_PORTFOLIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        String image1 = object.getString("image1");
                        String image2 = object.getString("image2");
                        String image3 = object.getString("image3");
                        String image4 = object.getString("image4");
                        String image5 = object.getString("image5");
                        String image6 = object.getString("image6");
                        String image7 = object.getString("image7");
                        String image8 = object.getString("image8");

                        Picasso.get().load(image1).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage1);
                        Picasso.get().load(image2).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage2);
                        Picasso.get().load(image3).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage3);
                        Picasso.get().load(image4).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage4);
                        Picasso.get().load(image5).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage5);
                        Picasso.get().load(image6).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage6);
                        Picasso.get().load(image7).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage7);
                        Picasso.get().load(image8).placeholder(R.drawable.ic_launcher_background).into(binding.portfolioImage8);



                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Worker_profile.this, "connection error..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("id",id+"");


                return  map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Worker_profile.this);
        queue.add(request);









    }

    private void showRating(int ratings) {



        if (ratings>=20 && ratings <=30){
         binding.workerDetailsStar1.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar2.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar3.setImageResource(R.drawable.baseline_star_border_24);
            binding.workerDetailsStar4.setImageResource(R.drawable.baseline_star_border_24);
            binding.workerDetailsStar5.setImageResource(R.drawable.baseline_star_border_24);

        } else if (ratings>=30 && ratings<=50) {
            binding.workerDetailsStar1.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar2.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar3.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar4.setImageResource(R.drawable.baseline_star_border_24);
            binding.workerDetailsStar5.setImageResource(R.drawable.baseline_star_border_24);
        } else if (ratings>=50 && ratings<=100) {
            binding.workerDetailsStar1.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar2.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar3.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar4.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar5.setImageResource(R.drawable.baseline_star_border_24);
        }
        else if (ratings>=100) {
            binding.workerDetailsStar1.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar2.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar3.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar4.setImageResource(R.drawable.baseline_star_24);
            binding.workerDetailsStar5.setImageResource(R.drawable.baseline_star_24);
        }




    }

    @Override
    public void onBackPressed() {
     super.onBackPressed();
    }
}