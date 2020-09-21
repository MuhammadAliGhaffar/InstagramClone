package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }

    }
    public void signupIsPressed(View view){

        if(edtSignUpUsername.getText().toString().equals("") || edtSignUpEmail.getText().toString().equals("") || edtSignUpPassword.getText().toString().equals("")){
            FancyToast.makeText(SignUpActivity.this,"Please fil out all fields",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

        }else{
            try{
                //Sign up with parse
                ParseUser user =new ParseUser();

                user.setUsername(edtSignUpUsername.getText().toString());
                user.setEmail(edtSignUpEmail.getText().toString());
                user.setPassword(edtSignUpPassword.getText().toString());

                final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Signing up "+edtSignUpUsername.getText().toString());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
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

                            FancyToast.makeText(SignUpActivity.this,"Error Account Creation failed account could not be created :"+e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                        }
                        progressDialog.dismiss();
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void rootLayoutTappedSignUp(View view){
        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}