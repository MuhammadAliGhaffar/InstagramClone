package com.example.instagramclone;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView txtSignUp;

    private String n1,n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp=findViewById(R.id.txtSignUp);
        n1=getColoredSpanned("Dont have an account?","#808080");
        n2=getColoredSpanned(" Sign Up.","#000");
        txtSignUp.setText(Html.fromHtml(n1+" "+n2));

    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}