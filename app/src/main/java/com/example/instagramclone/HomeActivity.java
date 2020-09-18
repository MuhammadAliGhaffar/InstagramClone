package com.example.instagramclone;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends AppCompatActivity {

    private TextView textView3;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*textView3=findViewById(R.id.textView3);
        textView3.setText(ParseUser.getCurrentUser().getUsername().toString());*/


        HomeActivity.this.setTitle("Instagram");




        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileTab()).commit();

        mBottomNavigationView=findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;

                switch (item.getItemId()){
                    case R.id.users:
                        selectedFragment=new UsersTab();
                        FancyToast.makeText(HomeActivity.this,"Users is Tapped",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
                    case R.id.upload:
                        selectedFragment=new SharePictureTab();
                        FancyToast.makeText(HomeActivity.this,"Upload is Tapped",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
                    case R.id.profile:
                        selectedFragment=new ProfileTab();
                        FancyToast.makeText(HomeActivity.this,"Profile is Tapped",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });

    }



}