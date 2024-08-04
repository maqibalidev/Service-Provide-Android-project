package com.example.tamir_project;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.databinding.ActivityRegisterBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.shadow.ShadowRenderer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
TextView got_to_login;
ActivityRegisterBinding binding;
    String[] items =  {"Sargodha","Lahore"};
CustomProgressDialog dialog;
    FusedLocationProviderClient client;
    ArrayAdapter<String> adapterItems;
    String UPLOAD_USER_ADDRESS =Apis_url.BASE_URL + Apis_url.GET_USER_ADDRESS;
Uri image_uri;
    String num;
    String encodeImage,name,nick_name,email,pass,city;
    String URL = Apis_url.BASE_URL + Apis_url.SIGNUP_USER;
    String SIGNIN_URL = Apis_url.BASE_URL + Apis_url.SIGNIN_USER;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
getSupportActionBar().hide();
        num =getIntent().getStringExtra("number");

    dialog=new CustomProgressDialog(Register.this);



        client = LocationServices.getFusedLocationProviderClient(Register.this);




        int inputtype =binding.registerPassword.getInputType();

        binding.registerProfileEyeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(binding.registerPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)){

                    binding.registerPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.registerHideLine.setVisibility(View.VISIBLE);

                }
                else {
                    binding.registerPassword.setInputType(inputtype);
                    binding.registerHideLine.setVisibility(View.GONE);

                }
//

            }
        });




binding.goToLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(Register.this,Login.class));
    }
});

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        binding.autoCompleteTxt.setAdapter(adapterItems);

        binding.autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                binding.autoCompleteTxt.setText(item);
            }
        });

     binding.registerBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {


             if (binding.regsiterName.getText().toString().isEmpty()){

                 binding.regsiterName.setError("please enter name");

             }
             else if (binding.regsiterNickName.getText().toString().isEmpty()){

                 binding.regsiterNickName.setError("please enter name");

             }
             else if (binding.regsiterEmail.getText().toString().isEmpty()){

                 binding.regsiterEmail.setError("please enter name");

             }

             else if (binding.registerPassword.getText().toString().isEmpty()){

                 binding.registerPassword.setError("please enter name");

             }
             else if (binding.autoCompleteTxt.getText().toString().isEmpty()){

                 binding.autoCompleteTxt.setError("please enter name");

             }
             else if (encodeImage == null){

                 name= binding.regsiterName.getText().toString();
                 email =binding.regsiterEmail.getText().toString();
                 pass = binding.registerPassword.getText().toString();
                 city=binding.autoCompleteTxt.getText().toString();
                 nick_name=binding.regsiterNickName.getText().toString();
              dialog.showDialog("Signing Account","Please wait..");
                signInuserWithImage();

             }
             else if (encodeImage !=null){

                 name= binding.regsiterName.getText().toString();
                 email =binding.regsiterEmail.getText().toString();
                 pass = binding.registerPassword.getText().toString();
                 city=binding.autoCompleteTxt.getText().toString();
                 nick_name=binding.regsiterNickName.getText().toString();



                 dialog.showDialog("Signing Account","Please wait..");
             signInuserWithImage();


             }

         }
     });


binding.registerImageSelectBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

   Dexter.withContext(Register.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
       @Override
       public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

        try {

            Intent intent =new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,100);


        }catch (Exception e){

            e.printStackTrace();

        }
       }

       @Override
       public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

       }

       @Override
       public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
           permissionToken.continuePermissionRequest();
       }
   }).check();


    }
});


    }

    private void signInuserWithImage() {

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
if (!response.equals("error")){

signin_user();




    finish();



dialog.dismissDialog();
}
else {
    Toast.makeText(Register.this, "failed to create profile", Toast.LENGTH_SHORT).show();
    dialog.dismissDialog();
}


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
                dialog.dismissDialog();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map = new HashMap<String, String>();
               map.put("name",name);
               map.put("nick_name",nick_name);
               map.put("city",city);
               map.put("email",email);
               map.put("pass",pass);
               map.put("phone",num);

if (encodeImage!=null){
    map.put("image",encodeImage);

}
else {
    map.put("image","0");

}
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(request);

    }
    private void signin_user() {

        StringRequest request = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
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

                        if (binding.registerCheckOption.isChecked()){
                            SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                            SharedPreferences.Editor editor =preferences.edit();
                            editor.putInt("id",id);
                            editor.putString("name",name);
                            editor.putString("email",email);
                            editor.putString("phone",phone);
                            editor.putString("image",image);
                            editor.putString("city",city);

                            editor.putBoolean("islogin", true);


                            editor.apply();
                        }



                            Dexter.withContext(Register.this)
                                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                        getmylocation();


                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                        Toast.makeText(Register.this, "permission denied", Toast.LENGTH_SHORT).show();
                                        SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                                        SharedPreferences.Editor editor =preferences.edit();
                                        editor.putBoolean("islogin",true);

                                        editor.commit();

                                        startActivity(new Intent(Register.this,Navigation_bottom.class));
                                        finish();

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
                    Toast.makeText(Register.this, "Incorrect number or password", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("phone",num);
                map.put("pass",pass);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(request);

    }
    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {

                if (location!=null){
                    Geocoder geocoder =new Geocoder(Register.this);
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
                                    Geocoder geocoder =new Geocoder(Register.this);
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
                        Intent intent = new Intent(Register.this,Navigation_bottom.class);

                        startActivity(intent);
finish();
                    }
                    else {
                        dialog.dismissDialog();
                        Intent intent = new Intent(Register.this,Navigation_bottom.class);




                        startActivity(intent);
                        finish();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismissDialog();
                    Intent intent = new Intent(Register.this,Navigation_bottom.class);




                    startActivity(intent);
finish();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();

                    SharedPreferences preferences2 = Objects.requireNonNull(Register.this).getSharedPreferences("address",MODE_PRIVATE);


                    SharedPreferences user_data = Register.this.getSharedPreferences("user_data",MODE_PRIVATE);

                    map.put("id",user_data.getInt("id",0)+"");
                    map.put("adress",preferences2.getString("realAddress",""));
                    map.put("longitude",preferences2.getString("longitude",""));

                    map.put("latitude",preferences2.getString("latitude",""));



                    return map;
                }

            };

            RequestQueue queue =Volley.newRequestQueue(Register.this);
            queue.add(request);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode==RESULT_OK &&data.getData()!=null &&requestCode==100){

        try {
            image_uri=data.getData();
            InputStream inputStream= getContentResolver().openInputStream(image_uri);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            binding.registerImage.setImageBitmap(bitmap);
encodeBitmapImage(bitmap);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    else{
        Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
    }



    }

    private void encodeBitmapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        encodeImage = android.util.Base64.encodeToString(bytes, Base64.DEFAULT);


    }
}