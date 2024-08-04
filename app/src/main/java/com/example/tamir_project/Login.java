package com.example.tamir_project;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.databinding.ActivityLoginBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    TextView go_to_register, forgot_pass, remember_me;
    Button login_btn;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    GoogleMap map;
    String URL = Apis_url.BASE_URL + Apis_url.SIGNIN_USER;
    private int RANDOM_OTP;
    FirebaseAuth auth;
CheckBox checkBox;

    CustomProgressDialog dialog;
    ActivityLoginBinding binding;
    EditText editTextPhone;
    LocationManager locationManager;
    String phone, pass;
    Location loc;

    String UPLOAD_USER_ADDRESS =Apis_url.BASE_URL + Apis_url.GET_USER_ADDRESS;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

     dialog=new CustomProgressDialog(Login.this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        go_to_register = findViewById(R.id.go_to_register);
        forgot_pass = findViewById(R.id.forgot_pass);
        remember_me = findViewById(R.id.rember_me_login);
        login_btn = findViewById(R.id.login_btn);
        editTextPhone = findViewById(R.id.editTextPhone);
checkBox=findViewById(R.id.checkBox);
        auth = FirebaseAuth.getInstance();


        client = LocationServices.getFusedLocationProviderClient(Login.this);

        int inputtype =binding.editLoginPassword.getInputType();

        binding.loginProfileEyeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(binding.editLoginPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)){

                    binding.editLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.loginHideLine.setVisibility(View.VISIBLE);

                }
                else {
                    binding.editLoginPassword.setInputType(inputtype);
                    binding.loginHideLine.setVisibility(View.GONE);

                }
//

            }
        });


////////////////////////   Text Underling   //////////////////////////////
        remember_me.setPaintFlags(remember_me.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        go_to_register.setPaintFlags(go_to_register.getPaintFlags() |Paint.UNDERLINE_TEXT_FLAG);


        SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
if (preferences.getBoolean("islogin", false)==true){

    startActivity(new Intent(Login.this,Navigation_bottom.class));
    finish();
}




login_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


if (binding.editLoginPassword.getText().toString().isEmpty()){
    binding.editLoginPassword.setText("please enter password");
}
else if (binding.editTextPhone.getText().toString().isEmpty()){
    binding.editTextPhone.setText("please enter number");
}
else {
phone= binding.editTextPhone.getText().toString().trim();
 pass = binding.editLoginPassword.getText().toString().trim();
    dialog.showDialog("Signin Account","Please wait..");
    signin_user();
}


    }
});









        //////////////  GO TO REGISTER   /////////////////


        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Set_profile.class));

            }
        });




        ////////////////// FORGOT PASS /////////////////////
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextPhone.getText().toString().isEmpty()){
                    editTextPhone.setError("please enter phone number");
                } else if (editTextPhone.getText().toString().length()<12) {
                    editTextPhone.setError("invalid number");
                }
else {
                    startActivity(new Intent(Login.this, Set_Password.class));

                }
            }
        });


    }
    private void signin_user() {

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("error")){


                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject ob =array.getJSONObject(0);
                        int id =ob.getInt("id");
                        String name = ob.getString("user_name");
                        String email =ob.getString("user_email");
                        String phone =ob.getString("user_phone");
                        String image = ob.getString("user_image");
                        String city = ob.getString("city");

                           SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                           SharedPreferences.Editor editor =preferences.edit();
                           editor.putInt("id",id);
                           editor.putString("name",name);
                           editor.putString("email",email);
                           editor.putString("phone",phone);
                           editor.putString("image",image);
                           editor.putString("city",city);

                           editor.apply();



                        Dexter.withContext(Login.this)
                                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        getmylocation();



                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                       dialog.dismissDialog();
                                        Toast.makeText(Login.this, "permission denied", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this,Varification.class);
                                        intent.putExtra("number",editTextPhone.getText().toString());
                                        intent.putExtra("check",1);
if(checkBox.isChecked()){
    intent.putExtra("checked",true);
}
                                        startActivity(intent);


                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                        permissionToken.continuePermissionRequest();
                                    }
                                }).check();





                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }







                }
                else {
                   dialog.dismissDialog();
                    Toast.makeText(Login.this, "Incorrect number or password", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               dialog.dismissDialog();
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("phone",phone);
                map.put("pass",pass);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(request);

    }
    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {

             if (location!=null){
                 Geocoder geocoder =new Geocoder(Login.this);
                 try {
                     ArrayList<Address> arradr= (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);


                 if (arradr != null){
                     SharedPreferences preferences = getSharedPreferences("address",MODE_PRIVATE);
                     SharedPreferences.Editor editor =preferences.edit();
                     editor.putString("longitude",location.getLongitude()+"");
                     editor.putString("latitude",location.getLatitude()+"");
                     editor.putString("realAddress",arradr.get(0).getThoroughfare()+ " " +arradr.get(0).getLocality()+ " " +arradr.get(0).getAdminArea()+ " "+arradr.get(0).getCountryName());
                     editor.apply();
                     update_user_adress();
                 }

                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }

             }
             else {

                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                     LocationRequest locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 100).setWaitForAccurateLocation(false).setMinUpdateIntervalMillis(1000)
                             .setMaxUpdateDelayMillis(100).build();
                     LocationCallback locationCallback = new LocationCallback() {
                         @Override
                         public void onLocationResult(@NonNull LocationResult locationResult) {
                             if (locationRequest == null) {
                                 return;
                             }
                         }
                     };
                     if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                         // TODO: Consider calling
                         //    ActivityCompat#requestPermissions
                         // here to request the missing permissions, and then overriding
                         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                         //                                          int[] grantResults)
                         // to handle the case where the user grants the permission. See the documentation
                         // for ActivityCompat#requestPermissions for more details.
                         return;
                     }
                     LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, locationCallback, null);

                     task.addOnSuccessListener(new OnSuccessListener<Location>() {
                         @Override
                         public void onSuccess(final Location location) {

                             if (location!=null){
                                 Geocoder geocoder =new Geocoder(Login.this);
                                 try {
                                     ArrayList<Address> arradr= (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);


                                     SharedPreferences preferences = getSharedPreferences("address",MODE_PRIVATE);
                                     SharedPreferences.Editor editor =preferences.edit();
                                     editor.putString("longitude",location.getLongitude()+"");
                                     editor.putString("latitude",location.getLatitude()+"");
                                     editor.putString("realAddress",arradr.get(0).getThoroughfare()+ " " +arradr.get(0).getLocality()+ " " +arradr.get(0).getAdminArea()+ " "+arradr.get(0).getCountryName());
                                     editor.apply();

                                     update_user_adress();
                                 } catch (IOException e) {
                                     throw new RuntimeException(e);
                                 }

                             }

                         }
                     });}





                 SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);

                 if (preferences.contains("phone")){
                     Intent intent = new Intent(Login.this,Varification.class);
                     intent.putExtra("number",editTextPhone.getText().toString());
                     intent.putExtra("check",1);
                     if(checkBox.isChecked()){
                         intent.putExtra("checked",true);
                     }

                     startActivity(intent);

                 }



             }
            }
        });}
    private void update_user_adress() {

        try {

            StringRequest request =new StringRequest(Request.Method.POST, UPLOAD_USER_ADDRESS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
if (response.equals("success")){
    dialog.dismissDialog();
    Intent intent = new Intent(Login.this,Varification.class);
    intent.putExtra("number",editTextPhone.getText().toString());
    intent.putExtra("check",1);
    if(checkBox.isChecked()){
        intent.putExtra("checked",true);
    }
    startActivity(intent);

}
else {
    dialog.dismissDialog();
    Intent intent = new Intent(Login.this,Varification.class);
    intent.putExtra("number",editTextPhone.getText().toString());
    intent.putExtra("check",1);
    if(checkBox.isChecked()){
        intent.putExtra("checked",true);
    }
    startActivity(intent);

}
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismissDialog();
                    Intent intent = new Intent(Login.this,Varification.class);
                    intent.putExtra("number",editTextPhone.getText().toString());
                    intent.putExtra("check",1);
                    if(checkBox.isChecked()){
                        intent.putExtra("checked",true);
                    }
                    startActivity(intent);

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();

                    SharedPreferences preferences2 = Objects.requireNonNull(Login.this).getSharedPreferences("address",MODE_PRIVATE);


                    SharedPreferences user_data = Login.this.getSharedPreferences("user_data",MODE_PRIVATE);

                    map.put("id",user_data.getInt("id",0)+"");
                    map.put("adress",preferences2.getString("realAddress",""));
                    map.put("longitude",preferences2.getString("longitude",""));

                    map.put("latitude",preferences2.getString("latitude",""));



                    return map;
                }

            };

            RequestQueue queue =Volley.newRequestQueue(Login.this);
            queue.add(request);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}