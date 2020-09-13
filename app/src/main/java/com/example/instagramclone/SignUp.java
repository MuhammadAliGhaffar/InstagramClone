package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {

    private EditText edt_id,edt_name,edt_father;
    private TextView txtGetData;
    private String allStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edt_id=findViewById(R.id.edt_id);
        edt_name=findViewById(R.id.edt_name);
        edt_father=findViewById(R.id.edt_father);
        txtGetData=findViewById(R.id.txtGetData);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery= ParseQuery.getQuery("Student");
                parseQuery.getInBackground("A7PQgzhZNU", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object !=null && e==null){
                            txtGetData.setText(object.get("student_name")+"");
                        }
                    }
                });
            }
        });

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

    public void getAllData(View view){

        allStudent="";

        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Student");
        queryAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0){

                        for(ParseObject student : objects){
                            allStudent = allStudent + student.get("student_name") + student.get("student_father") + student.get("student_id") + "\n";

                        }

                        FancyToast.makeText(SignUp.this,allStudent,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                    }else {
                        FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                    }
                }
            }
        });
    }

}