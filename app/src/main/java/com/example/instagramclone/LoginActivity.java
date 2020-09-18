package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {

    private TextView txtSignUp;
    private EditText edtLoginEmail,edtLoginPassword;

    private String n1,n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp=findViewById(R.id.txtSignUp);
        n1=getColoredSpanned("Dont have an account?","#808080");
        n2=getColoredSpanned(" Sign Up.","#000");
        txtSignUp.setText(Html.fromHtml(n1+" "+n2));


        edtLoginEmail=findViewById(R.id.edtLoginEmail);
        edtLoginPassword=findViewById(R.id.edtLoginPassword);

        if(ParseUser.getCurrentUser() != null){
         //   ParseUser.getCurrentUser().logOut();
            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    public void takeMeToSignUp(View view){
        Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);

    }

    public void loginIsPressed(View view){

        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(edtLoginEmail.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")){
            FancyToast.makeText(LoginActivity.this,"Please fil out all fields",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

        }else {
            final ProgressDialog progressDialogg=new ProgressDialog(LoginActivity.this);
            progressDialogg.setMessage("Logging in");
            progressDialogg.show();
            ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {

                @Override
                public void done(ParseUser parseuser, ParseException e) {
                    if(parseuser != null) {
                        if(parseuser.getBoolean("emailVerified")){
                            FancyToast.makeText(LoginActivity.this,"Welcome " + parseuser.getUsername() + " !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {

                            ParseUser.logOut();
                            FancyToast.makeText(LoginActivity.this,"Login Fail please verify your email first",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                        }
                    }else{

                        ParseUser.logOut();
                        FancyToast.makeText(LoginActivity.this,"Login Fail "+e.getMessage()+"please re-try",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                    }
                    progressDialogg.dismiss();
                }
            });
        }


    }


    public void rootLayoutTappedLogin(View view){
        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}