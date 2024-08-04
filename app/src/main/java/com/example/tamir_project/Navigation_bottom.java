package com.example.tamir_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tamir_project.Adapters.Adapter_nearby_service;
import com.example.tamir_project.Models.Models_nearby_service;
import com.example.tamir_project.databinding.ActivityNavigationBottomBinding;
import com.example.tamir_project.ui.Chats.ChatsFragment;
import com.example.tamir_project.ui.add_Task.TaskFragment;
import com.example.tamir_project.ui.home_bottom_nav.Home_bottom_navFragment;
import com.example.tamir_project.ui.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Navigation_bottom extends AppCompatActivity {

    private ActivityNavigationBottomBinding binding;
    TextView toolbar_title,fixed_labour,plumber,electrition,constructor,material_provider,painter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    Toolbar toolbar;
    ImageView setting_option,back_arrow_option,toolbar_noti_option;
    SearchView searchView;
    BottomNavigationView navView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBottomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
auth=FirebaseAuth.getInstance();
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_chats, R.id.navigation_task,R.id.navigation_profile)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation_bottom);
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//
toolbar_title=findViewById(R.id.toolbar_title);
searchView=findViewById(R.id.searchView);
        setting_option=findViewById(R.id.setting_option);
//        searchView.setQueryHint("Search services here");
        back_arrow_option=findViewById(R.id.back_arrow_option);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.drawer_navigation);
        toolbar=findViewById(R.id.toolbar);
linearLayout=findViewById(R.id.drawer_top);
        toolbar_noti_option=findViewById(R.id.toolbar_noti_option);
setSupportActionBar(toolbar);



fixed_labour=findViewById(R.id.fixed_labour);
constructor=findViewById(R.id.constructor);
painter=findViewById(R.id.painter);
electrition=findViewById(R.id.electrition);
plumber=findViewById(R.id.plumber);
material_provider=findViewById(R.id.matrial_provider);
searchView=findViewById(R.id.searchView);

searchView.setOnCloseListener(new SearchView.OnCloseListener() {
    @Override
    public boolean onClose() {
       searchView.setFocusable(false);
        return false;
    }
});

searchView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        searchView.setFocusable(true);
    }
});


searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String s) {

        Intent intent = new Intent(Navigation_bottom.this,Fixed_labour.class);
        intent.putExtra("title","Search results");
        intent.putExtra("check",1);
        intent.putExtra("search",s);
        startActivity(intent);




        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
});





fixed_labour.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
        intent.putExtra("title","Fixed Labour");
        startActivity(intent);

    }
});
        painter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
                intent.putExtra("title",painter.getText().toString());
                startActivity(intent);
            }
        });
        constructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
                intent.putExtra("title",constructor.getText().toString());
                startActivity(intent);

            }
        });

        material_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
                intent.putExtra("title",material_provider.getText().toString());
                startActivity(intent);

            }
        });

        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
                intent.putExtra("title",plumber.getText().toString());
                startActivity(intent);

            }
        });

        electrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Navigation_bottom.this,Fixed_labour.class);
                intent.putExtra("title",electrition.getText().toString());
                startActivity(intent);

            }
        });














        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);

drawerLayout.addDrawerListener(toggle);

toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
toggle.syncState();





        Animation anim = AnimationUtils.loadAnimation(this,R.anim.drawer_top_anim);
        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.close_drawer_anim);
        navigationView.getMenu().getItem(0).setActionView(R.layout.drawer_first_item_image);

        toolbar_noti_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation_bottom.this,Notifications.class));

            }
        });



navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       if (item.getItemId()==R.id.drawer_service_provider){

           linearLayout.setVisibility(View.VISIBLE);

           linearLayout.startAnimation(anim);
navigationView.getMenu().getItem(0).setEnabled(false);
           navigationView.getMenu().getItem(1).setEnabled(false);
           navigationView.getMenu().getItem(2).setEnabled(false);
           navigationView.getMenu().getItem(3).setEnabled(false);
           navigationView.getMenu().getItem(4).setEnabled(false);
           navigationView.getMenu().getItem(5).setEnabled(false);
       }
       else if(item.getItemId()==R.id.drawer_term_contition){
           startActivity(new Intent(Navigation_bottom.this,Terms_and_conditions.class));


       }
       else if(item.getItemId()==R.id.drawer_privacy_policy){
           startActivity(new Intent(Navigation_bottom.this,Privacy_policy.class));


       }
       else if(item.getItemId()==R.id.drawer_settings){
           startActivity(new Intent(Navigation_bottom.this,Settings.class));

       } else if (item.getItemId()==R.id.drawer_privacy_logout) {
           SharedPreferences preferences =getSharedPreferences("user_data",MODE_PRIVATE);
           SharedPreferences preferences2 =getSharedPreferences("address",MODE_PRIVATE);

           SharedPreferences.Editor editor =preferences.edit();
           SharedPreferences.Editor editor2 =preferences2.edit();
           editor.clear();
           editor2.clear();
editor.commit();
           editor2.commit();
           startActivity(new Intent(Navigation_bottom.this,Login.class));
          finish();
       }




        return true;

    }
});


back_arrow_option.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        linearLayout.startAnimation(anim2);
       linearLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        navigationView.getMenu().getItem(0).setEnabled(true);
        navigationView.getMenu().getItem(1).setEnabled(true);
        navigationView.getMenu().getItem(2).setEnabled(true);
        navigationView.getMenu().getItem(3).setEnabled(true);
        navigationView.getMenu().getItem(4).setEnabled(true);
        navigationView.getMenu().getItem(5).setEnabled(true);
    }
});



drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                linearLayout.setVisibility(View.GONE);
                navigationView.getMenu().getItem(0).setEnabled(true);
                navigationView.getMenu().getItem(1).setEnabled(true);
                navigationView.getMenu().getItem(2).setEnabled(true);
                navigationView.getMenu().getItem(3).setEnabled(true);
                navigationView.getMenu().getItem(4).setEnabled(true);
                navigationView.getMenu().getItem(5).setEnabled(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });




        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.nav_host_fragment_activity_navigation_bottom,new Home_bottom_navFragment());
        transaction.commit();

navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.navigation_home){
            setting_option.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            toolbar_title.setVisibility(View.GONE);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
   transaction.replace(R.id.nav_host_fragment_activity_navigation_bottom,new Home_bottom_navFragment());
            transaction.commit();
        }
        else if (item.getItemId()==R.id.navigation_chats){
            searchView.setVisibility(View.GONE);
            setting_option.setVisibility(View.GONE);
           toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText("Chats");
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_navigation_bottom,new ChatsFragment());
            transaction.commit();
        }
       else if (item.getItemId()==R.id.navigation_task){
            searchView.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
            setting_option.setVisibility(View.GONE);
            toolbar_title.setText("Add new task");
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();

            transaction.replace(R.id.nav_host_fragment_activity_navigation_bottom,new TaskFragment());
transaction.commit();
        }
        else if (item.getItemId()==R.id.navigation_profile){
            searchView.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
            setting_option.setVisibility(View.GONE);
            toolbar_title.setText("Profile");
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();

            transaction.replace(R.id.nav_host_fragment_activity_navigation_bottom,new Profile());
            transaction.commit();
        }


        return true;
    }
});






    }

    @Override
    public void onBackPressed() {
        if (!(navView.getSelectedItemId() ==R.id.navigation_home)){
navView.setSelectedItemId(R.id.navigation_home);
            setting_option.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            toolbar_title.setVisibility(View.GONE);
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_navigation_bottom,new Home_bottom_navFragment());
            transaction.commit();
        }
        else {
            finish();
        }
    }
}