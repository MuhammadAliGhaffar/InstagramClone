package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FancyToast.makeText(HomeActivity.this,"Logout is Pressed",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                ParseUser.getCurrentUser().logOut();
                Intent intent =new Intent(HomeActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private BottomNavigationView mBottomNavigationView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       mToolbar=findViewById(R.id.actiontoolbar);
       setSupportActionBar(mToolbar);
       getSupportActionBar().setTitle("Instagram");







        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeTab()).commit();

        mBottomNavigationView=findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;

                switch (item.getItemId()){
                    case R.id.home1:
                        selectedFragment=new HomeTab();
                        FancyToast.makeText(HomeActivity.this,"Home is Tapped",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        break;
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