package com.example.tamir_project.ui.Chats;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Adapters.Adapter_chats;
import com.example.tamir_project.Apis_url;
import com.example.tamir_project.Models.Model_Chats;
import com.example.tamir_project.Models.Model_Chatting;
import com.example.tamir_project.R;
import com.example.tamir_project.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatsFragment extends Fragment {
String URL = Apis_url.BASE_URL+Apis_url.GET_RECIEVER_DATA;
    private FragmentChatsBinding binding;
FirebaseAuth auth;
boolean seen_msg;
    LinearLayout progress,emptyChats_layout;
    RecyclerView recyclerView;
    Adapter_chats adapter;
FirebaseDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatsViewModel chatsViewModel =
                new ViewModelProvider(this).get(ChatsViewModel.class);

        //////////////////   Declaration    /////////////////////////////


        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

auth=FirebaseAuth.getInstance();
database=FirebaseDatabase.getInstance();

        ////////////////////////   Finding Ids   /////////////////////////////////

        recyclerView=root.findViewById(R.id.recyc_chats);

progress=root.findViewById(R.id.chatting_progress);
emptyChats_layout=root.findViewById(R.id.empty_chats_layout);


        ///////////////////////   setting chats recyclerView    //////////////////////////////////

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Model_Chats> list=new ArrayList<>();


        database.getReference().child("chats").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        String reciever_id = snapshot1.child("reciever_id").getValue(String.class);
                        if (reciever_id.equals("0")) {

                        } else {


                            database.getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + reciever_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String last_msg = snapshot.child("last_msg").getValue(String.class);
                                    String time = snapshot.child("last_msg_time").getValue(String.class);
                                    String rec_id = snapshot.child("reciever_id").getValue(String.class);
                                    String seen = snapshot.child("seen").getValue(String.class);


                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

                                    String date = simpleDateFormat.format(Long.parseLong(time));


                                    try {

                                        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONArray array = new JSONArray(response);
                                                    for (int i = 0; i < array.length(); i++) {


                                                        JSONObject object = array.getJSONObject(i);

                                                        String name = object.getString("worker_name");
                                                        String image = object.getString("worker_image");

                                                        progress.setVisibility(View.GONE);
                                                        list.add(new Model_Chats(name, last_msg, date, rec_id, image, seen));

                                                        adapter = new Adapter_chats(list, getContext());

                                                        recyclerView.setAdapter(adapter);
                                                    }

                                                    adapter.notifyDataSetChanged();


                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }


                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            @Nullable
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> map = new HashMap<String, String>();
                                                if (!rec_id.equals("0")) {
                                                    map.put("id", rec_id);
                                                }
                                                return map;
                                            }
                                        };

                                        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                                        queue.add(request);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    emptyChats_layout.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);
                                }
                            });
                        }


                    }


                }
else {
                    emptyChats_layout.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }
            }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
















        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}