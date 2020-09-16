package com.example.instagramclone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView3=findViewById(R.id.textView3);
        textView3.setText(ParseUser.getCurrentUser().getUsername().toString());
    }
}