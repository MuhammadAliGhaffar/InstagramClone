package com.example.instagramclone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

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
            case R.id.camera:
                FancyToast.makeText(HomeActivity.this,"Camera is Tapped",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

                if(android.os.Build.VERSION.SDK_INT>=23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},3000);
                }else {
                    captureCamera();
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4000 && resultCode == RESULT_OK && data != null){
            try {
                Uri capturedImage =data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),capturedImage);
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();

                ParseFile parseFile =new ParseFile("img.png",bytes);
                ParseObject parseObject=new ParseObject("Photo");
                parseObject.put("picture",parseFile);

                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog progressDialog =new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Uploading...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(HomeActivity.this,"Done", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                        }else {
                            FancyToast.makeText(HomeActivity.this,"Error: "+e.getMessage(), FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        }

                        progressDialog.dismiss();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void captureCamera() {
        Intent intent =new Intent(Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==3000){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                captureCamera();
            }
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