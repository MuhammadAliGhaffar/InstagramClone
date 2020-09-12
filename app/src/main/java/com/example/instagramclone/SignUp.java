package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {

    private EditText edt_id,edt_name,edt_father;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        edt_father=findViewById(R.id.edt_father);
    }



    public void ObjectSaved(View view){
        try{
            final ParseObject Student =new ParseObject("Student");
            Student.put("student_id",Integer.parseInt(edt_id.getText().toString()));
            Student.put("student_name",edt_name.getText().toString());
            Student.put("student_father",edt_father.getText().toString());

            Student.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        //Toast.makeText(SignUp.this,"Object is saved",Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this,Student.get("name") + "object is saved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                    }else {
                        FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                    }
                }
            });
        }catch (Exception e){
            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

        }


    }

}