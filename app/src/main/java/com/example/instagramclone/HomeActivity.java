package com.example.instagramclone;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends AppCompatActivity {

    private TextView textView3;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView3=findViewById(R.id.textView3);
        textView3.setText(ParseUser.getCurrentUser().getUsername().toString());

        mBottomNavigationView=findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        FancyToast.makeText(HomeActivity.this,"User is Clicked",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
                    case R.id.upload:
                        FancyToast.makeText(HomeActivity.this,"Upload is Clicked",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
                    case R.id.profile:
                        FancyToast.makeText(HomeActivity.this,"Profile is Clicked",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
                }
            }
        });


    }



}