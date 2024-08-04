package com.example.tamir_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.example.tamir_project.Adapters.Adapter_Chatting;
import com.example.tamir_project.Models.Model_Chatting;
import com.example.tamir_project.databinding.ActivityChattingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting extends AppCompatActivity {
RecyclerView recyclerView;
CircleImageView chatting_profile;
FirebaseStorage storage;
Uri image_uri;
TextView chatting_name;
EditText send_msg_editText;
ImageView send_msg_btn,chatting_back_btn,chatting_noti;
    Adapter_Chatting adapter;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ActivityChattingBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    getSupportActionBar().hide();
        chatting_name=findViewById(R.id.chatting_name);
    recyclerView=findViewById(R.id.recyc_chatting);
        chatting_profile=findViewById(R.id.chatting_profile);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        send_msg_editText=findViewById(R.id.send_msg_editText);
        send_msg_btn=findViewById(R.id.send_msg_btn);
        chatting_back_btn=findViewById(R.id.chatting_back_btn);
        chatting_noti=findViewById(R.id.chatting_noti);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
storage=FirebaseStorage.getInstance();

        Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.drawable.baseline_person_24).into(chatting_profile);
        chatting_name.setText(getIntent().getStringExtra("name"));
        String id = getIntent().getStringExtra("worker_id");

            SharedPreferences preferences2 =getSharedPreferences("user_data",MODE_PRIVATE);
String user_id=FirebaseAuth.getInstance().getUid();

        ArrayList<Model_Chatting> list=new ArrayList<>();


        chatting_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chatting.this,Notifications.class));
            }
        });
        chatting_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String SENDERROOM = user_id+id;
        String RECIEVERROOM=id+user_id;


//        binding.selectMsgImgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//
//
//            }
//        });



        database.getReference().child("chats").child(SENDERROOM).child("messages").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot snapshot1 :snapshot.getChildren()){

                    Model_Chatting model_chatting =snapshot1.getValue(Model_Chatting.class);





                    list.add(model_chatting);
                    HashMap<String,Object> hashMap3=new HashMap<>();

                 hashMap3.put("seen","1");

                    database.getReference().child("chats").child(SENDERROOM).updateChildren(hashMap3);



                    adapter=new Adapter_Chatting(list,Chatting.this);

                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (send_msg_editText.getText().toString().isEmpty()){
                    Toast.makeText(Chatting.this, "message is empty", Toast.LENGTH_SHORT).show();
                }
                else {






                    Date date =new Date();
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("last_msg",send_msg_editText.getText().toString());
                    hashMap.put("last_msg_time",date.getTime()+"");
                    hashMap.put("reciever_id",id+"");
                    hashMap.put("seen","0");

                    HashMap<String,Object> hashMap5=new HashMap<>();
                    hashMap5.put("last_msg",send_msg_editText.getText().toString());
                    hashMap5.put("last_msg_time",date.getTime()+"");
                    hashMap5.put("reciever_id","0");
                    hashMap5.put("seen","0");




                    database.getReference().child("chats").child(SENDERROOM).updateChildren(hashMap);
                    database.getReference().child("chats").child(RECIEVERROOM).updateChildren(hashMap5);

                    Model_Chatting modelChatting = new Model_Chatting(send_msg_editText.getText().toString(),date.getTime()+"",user_id,0);
                    database.getReference().child("chats").child(SENDERROOM).child("messages").push().setValue(modelChatting).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           if (task.isSuccessful()) {

                               database.getReference().child("chats").child(RECIEVERROOM).child("messages").push().setValue(modelChatting);

                               send_msg_editText.setText("");
                               recyclerView.scrollToPosition(list.size()-1);

                               }


                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Chatting.this, "connection error..", Toast.LENGTH_SHORT).show();
                       }
                   });






                }

            }
        });














    }





    @Override
    public void onBackPressed() {
   super.onBackPressed();
    }
}