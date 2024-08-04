package com.example.tamir_project.ui.home_bottom_nav;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.PRINT_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Adapters.Adapter_nearby_service;
import com.example.tamir_project.Adapters.Adapter_top_service_provider;
import com.example.tamir_project.Apis_url;
import com.example.tamir_project.MapsActivity;
import com.example.tamir_project.Models.Model_top_service_provide;
import com.example.tamir_project.Models.Models_nearby_service;
import com.example.tamir_project.R;
import com.example.tamir_project.databinding.FragmentHomeBottomnavBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Home_bottom_navFragment extends Fragment {

    private FragmentHomeBottomnavBinding binding;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView current_location_txt;
    LinearLayout  topServiceShimmerLayour,homeFragProgress,serviceNearbyShimmerLayour;
    String phone_num,email,pass;
    Adapter_top_service_provider adapter_top_service_provider;

    String URL =Apis_url.BASE_URL + Apis_url.GET_USER_DATA;

    String UPLOAD_USER_ADDRESS =Apis_url.BASE_URL + Apis_url.GET_USER_ADDRESS;

    String SERVICE_NEARBY =Apis_url.BASE_URL + Apis_url.SERVICE_NEARBY;


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Home_bottom_navViewModel homeViewModel =
                new ViewModelProvider(this).get(Home_bottom_navViewModel.class);
RecyclerView recyc_top_serviceer,recyc_nearby_service;
Adapter_top_service_provider adapter;


       String url= Apis_url.BASE_URL + Apis_url.TOP_SERVICES_PROVIDERS;

        binding = FragmentHomeBottomnavBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


topServiceShimmerLayour=root.findViewById(R.id.top_service_shimmer_layour);
serviceNearbyShimmerLayour=root.findViewById(R.id.service_nearby_shimmer_layour);
homeFragProgress=root.findViewById(R.id.homeFragProgress);


        current_location_txt=root.findViewById(R.id.current_location_home);
        recyc_top_serviceer =root.findViewById(R.id.recyc_home_top_services);
        recyc_nearby_service=root.findViewById(R.id.recyc_service_nearby);
       mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_home);















//        client = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));
//
//        Dexter.withContext(getContext())
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//
//                        getmylocation();
//
//
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                    }
//                }).check();



     SharedPreferences   preferences1 =getContext().getSharedPreferences("address",MODE_PRIVATE);
        if (preferences1.contains("longitude")) {

            String adrr= preferences1.getString("realAddress","");

current_location_txt.setText(adrr+"");
getmylocation(preferences1.getString("latitude",""),preferences1.getString("longitude",""));
        }
        else {

            current_location_txt.setText("Enable location permission in settings");
current_location_txt.setTextColor(Color.RED);

        }




        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyc_top_serviceer.setLayoutManager(linearLayoutManager);



        ArrayList<Model_top_service_provide> list=new ArrayList<>();


///////////////////////////////////////     TOP SERVICE PROVIDER REQUEST  START             //////////////////////////////////////////

    try {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                        list.add(new Model_top_service_provide(name, worker_profession, city, worker_image, worker_rating,id));
                        adapter_top_service_provider = new Adapter_top_service_provider(list, getContext());
                      topServiceShimmerLayour.setVisibility(View.GONE);
                       homeFragProgress.setVisibility(View.GONE);
                        recyc_top_serviceer.setAdapter(adapter_top_service_provider);

                    }


                } catch (JSONException e) {
                    Toast.makeText(getContext(), "connection error", Toast.LENGTH_SHORT).show();
                   homeFragProgress.setVisibility(View.GONE);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
           homeFragProgress.setVisibility(View.GONE);

                Toast.makeText(getContext(), "connection error..", Toast.LENGTH_SHORT).show();
            }


        });


        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
    catch (Exception e){
        e.printStackTrace();
    }

///////////////////////////////////////     TOP SERVICE PROVIDER REQUEST   END            //////////////////////////////////////////















        /////////////////////////     Setting  RecyclerVew For Nearby Service       //////////////////////////////////////


        LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());

        recyc_nearby_service.setLayoutManager(linearLayout);

        ArrayList<Models_nearby_service> list2=new ArrayList<>();


///////////////////////////////////////     SERVICE NEARBY REQUEST  START             //////////////////////////////////////////

try {
    StringRequest request2 = new StringRequest(Request.Method.POST, SERVICE_NEARBY, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);

                    String name = object.getString("worker_name");
                    int id = object.getInt("id");
                    String worker_city = object.getString("worker_city");
                    String worker_price = object.getString("worker_price");
                    String upload_time = object.getString("upload_time");
                    String worker_adress = object.getString("worker_adress");
                    String service_details = object.getString("service_details");
//                        String worker_latitude = object.getString("worker_latitude");
//                        String worker_longitude = object.getString("worker_longitude");
//
//                        double lati= Double.parseDouble(worker_longitude);
//                        double longi= Double.parseDouble(worker_longitude);

                    list2.add(new Models_nearby_service(name,
                            service_details,"Offered by: ",upload_time,"RS "+worker_price,
                            worker_adress,"Plumber",id));

                  serviceNearbyShimmerLayour.setVisibility(View.GONE);
                    homeFragProgress.setVisibility(View.GONE);

                    Adapter_nearby_service adapter = new Adapter_nearby_service(list2, getContext());
                    recyc_nearby_service.setMinimumHeight(array.length()*185);
                    recyc_nearby_service.setAdapter(adapter);



                }


            } catch (JSONException e) {
           homeFragProgress.setVisibility(View.GONE);
                Toast.makeText(getContext(), "connection error..", Toast.LENGTH_SHORT).show();

            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          homeFragProgress.setVisibility(View.GONE);

            Toast.makeText(getContext(), "connection error..", Toast.LENGTH_SHORT).show();
        }


    }){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            SharedPreferences preferences2 = getContext().getSharedPreferences("user_data",MODE_PRIVATE);

            Map<String,String> map2 =new HashMap<String,String>();

            map2.put("city",preferences2.getString("city",""));

            return map2;
        }
    };


    RequestQueue queue2 = Volley.newRequestQueue(getContext());
    queue2.add(request2);

}
catch (Exception e){
    Toast.makeText(getContext(), "connection error..", Toast.LENGTH_SHORT).show();
}



        recyc_nearby_service.setMinimumHeight(180*list2.size());
   recyc_nearby_service.setNestedScrollingEnabled(false);
        Adapter_nearby_service adapterNearbyService=new Adapter_nearby_service(list2, getContext());

recyc_nearby_service.setAdapter(adapterNearbyService);


        ///////////////////////////////////////     SERVICE NEARBY REQUEST  END             //////////////////////////////////////////



        return root;
    }

    private void update_user_adress() {

       try {

           StringRequest request =new StringRequest(Request.Method.POST, UPLOAD_USER_ADDRESS, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {

               }

           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           }){
               @Nullable
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> map = new HashMap<String,String>();

                   SharedPreferences preferences2 = Objects.requireNonNull(getContext()).getSharedPreferences("address",MODE_PRIVATE);


                   SharedPreferences user_data = getContext().getSharedPreferences("user_data",MODE_PRIVATE);

                   map.put("id",user_data.getInt("id",0)+"");
                   map.put("adress",preferences2.getString("realAddress",""));
                   map.put("longitude",preferences2.getString("longitude",""));
                   map.put("city",preferences2.getString("city",""));
                   map.put("latitude",preferences2.getString("latitude",""));



                   return map;
               }

           };

           RequestQueue queue =Volley.newRequestQueue(getContext());
           queue.add(request);

       }
       catch (Exception e){
           e.printStackTrace();
       }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getmylocation(String lat,String lon) {


                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        LatLng latLng=new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("You are here...!!");

                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));









            }
        });}

}