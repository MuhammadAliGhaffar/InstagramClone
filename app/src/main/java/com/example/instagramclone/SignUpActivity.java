package com.example.instagramclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSignUpUsername,edtSignUpEmail,edtSignUpPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtSignUpUsername=findViewById(R.id.edtSignUpUsername);
        edtSignUpEmail=findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword=findViewById(R.id.edtSignUpPassword);

    }
    public void signupIsPressed(View view){
        try{
            //Sign up with parse
            ParseUser user =new ParseUser();

            user.setUsername(edtSignUpUsername.getText().toString());
            user.setEmail(edtSignUpEmail.getText().toString());
            user.setPassword(edtSignUpPassword.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){

                        ParseUser.logOut();
                        FancyToast.makeText(SignUpActivity.this,"Account Created Successfully please verify your email before Login",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                        Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else{

                        FancyToast.makeText(SignUpActivity.this,"Error Account Creation failed account could not be created :"+e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void alertDisplayer(String title,String message,final boolean error){
        AlertDialog.Builder builder =new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    }
                });
        AlertDialog ok = builder.create();
        ok.show();

    }
}