package com.example.tamir_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.tamir_project.databinding.ActivityEditProfileBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Edit_profile extends AppCompatActivity {
EditText password;
ImageView eye_option;
    TextView toolbar_title;
    String UPDATE_USER_PROFILE =Apis_url.BASE_URL + Apis_url.UPDATE_USER_PROFILE;
    LinearLayout pass_hide_line;
    ImageView back_btn;
    Uri image_uri;
CustomProgressDialog dialog;

    String encodeImage="";
    ActivityEditProfileBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

dialog=new CustomProgressDialog(Edit_profile.this);

        password=findViewById(R.id.edit_profile_password);
        eye_option=findViewById(R.id.edit_profile_eye_option);
        back_btn=findViewById(R.id.back_btn_for_activity);
        toolbar_title=findViewById(R.id.activity_toolbar_title);
pass_hide_line=findViewById(R.id.password_hide_line);


        toolbar_title.setText("Edit Profile");
        SharedPreferences preferences = getSharedPreferences("user_data",Context.MODE_PRIVATE);
    binding.editTextTextPersonName.setText(preferences.getString("name",""));

binding.updateProfEmail.setText(preferences.getString("email",""));
binding.updateProfNum.setText(preferences.getString("phone",""));
 Picasso.get().load(preferences.getString("image","")).placeholder(R.drawable.baseline_person_24).into(binding.imageView7);


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.showDialog("Updating Profile","Please wait..");
                if (!encodeImage.equals("")) {
                    Log.d("encode",encodeImage);
  get_user_data();
                }
                else {

                    update_user_withoutImage();
                }

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        onBackPressed();
                finish();

            }
        });



        binding.editProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(Edit_profile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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


int inputtype =password.getInputType();

        eye_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)){

                   password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
pass_hide_line.setVisibility(View.VISIBLE);

               }
               else {
                   password.setInputType(inputtype);
                   pass_hide_line.setVisibility(View.GONE);

               }
//

            }
        });

    }

    private void update_user_withoutImage() {
        StringRequest request =new StringRequest(Request.Method.POST, UPDATE_USER_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject ob =array.getJSONObject(0);
                    int id =ob.getInt("id");
                    String name = ob.getString("user_name");
                    String email =ob.getString("user_email");
                    String phone =ob.getString("user_phone");
                    String image = ob.getString("user_image");


                    SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putInt("id",id);
                    editor.putString("name",name);
                    editor.putString("email",email);
                    editor.putString("phone",phone);
                    editor.putString("image",image);
                    editor.commit();
                    Toast.makeText(Edit_profile.this, "profile updated.", Toast.LENGTH_SHORT).show();
dialog.dismissDialog();

                } catch (JSONException e) {
                    dialog.dismissDialog();


                    Toast.makeText(Edit_profile.this, "Failef to update profile.", Toast.LENGTH_SHORT).show();
                }









            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog();

                Toast.makeText(Edit_profile.this, "conenction error..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                SharedPreferences preference3 = getSharedPreferences("user_data",MODE_PRIVATE);
                map.put("phone",binding.updateProfNum.getText().toString());
                map.put("id",preference3.getInt("id",0)+"");

                map.put("email",binding.updateProfEmail.getText().toString());
                map.put("pass",binding.editProfilePassword.getText().toString());
                map.put("name",binding.editTextTextPersonName.getText().toString());
                if (preference3.getString("image","").equals("no image")){
                    map.put("image_code","1");
                    map.put("image","no image");
                }
                else{
                    map.put("image_code","2");
                    map.put("image",preference3.getString("image",""));
                }

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(Edit_profile.this);
        queue.add(request);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void get_user_data() {

        StringRequest request =new StringRequest(Request.Method.POST, UPDATE_USER_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject ob =array.getJSONObject(0);
                    int id =ob.getInt("id");
                    String name = ob.getString("user_name");
                    String email =ob.getString("user_email");
                    String phone =ob.getString("user_phone");
                    String image = ob.getString("user_image");


                    SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putInt("id",id);
                    editor.putString("name",name);
                    editor.putString("email",email);
                    editor.putString("phone",phone);
                    editor.putString("image",image);
                    editor.commit();
                    Toast.makeText(Edit_profile.this, "profile updated.", Toast.LENGTH_SHORT).show();
                    dialog.dismissDialog();


                } catch (JSONException e) {
                    dialog.dismissDialog();


                    Toast.makeText(Edit_profile.this, "Failef to update profile.", Toast.LENGTH_SHORT).show();
                }









            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog();

                Toast.makeText(Edit_profile.this, "conenction error..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                SharedPreferences preference3 = getSharedPreferences("user_data",MODE_PRIVATE);
                map.put("phone",binding.updateProfNum.getText().toString());
                map.put("id",preference3.getInt("id",0)+"");

                map.put("email",binding.updateProfEmail.getText().toString());
                map.put("pass",binding.editProfilePassword.getText().toString());
                map.put("name",binding.editTextTextPersonName.getText().toString());
                map.put("image",encodeImage);
                map.put("image_code","0");
                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(Edit_profile.this);
        queue.add(request);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data.getData()!=null &&requestCode==100){

            try {
                image_uri=data.getData();
                InputStream inputStream= getContentResolver().openInputStream(image_uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.imageView7.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "image not selected", Toast.LENGTH_SHORT).show();
        }



    }

    private void encodeBitmapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        encodeImage = android.util.Base64.encodeToString(bytes, Base64.DEFAULT);


    }
}