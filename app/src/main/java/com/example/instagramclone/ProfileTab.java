package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtProfileBio,edtProfileProfession,edtProfileHobbies,edtProfileFavoriteSport;
    private Button btnUpdateInfo;
    private TextView textProfile;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName=view.findViewById(R.id.edtProfileName);
        edtProfileBio=view.findViewById(R.id.edtProfileBio);
        edtProfileProfession=view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies=view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavoriteSport=view.findViewById(R.id.edtProfileFavoriteSport);
        btnUpdateInfo=view.findViewById(R.id.btnUpdateInfo);
        textProfile=view.findViewById(R.id.textProfile);
        textProfile.setText(ParseUser.getCurrentUser().getUsername().toString());

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("profileName") == null || parseUser.get("profileBio") == null || parseUser.get("profileProfession") == null ||
                parseUser.get("profileHobbies") == null || parseUser.get("profileFavoriteSport") == null){
            edtProfileName.setHint("Enter a profile name");
            edtProfileBio.setHint("Enter your bio");
            edtProfileProfession.setHint("Enter your profession");
            edtProfileHobbies.setHint("Enter your hobbies");
            edtProfileFavoriteSport.setHint("Enter your favorite sport");
        }else {
            edtProfileName.setText(parseUser.get("profileName").toString());
            edtProfileBio.setText(parseUser.get("profileBio").toString());
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());
            edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());
            edtProfileFavoriteSport.setText(parseUser.get("profileFavoriteSport").toString());
        }


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("profileName",edtProfileName.getText().toString());
                parseUser.put("profileBio",edtProfileBio.getText().toString());
                parseUser.put("profileProfession",edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies",edtProfileHobbies.getText().toString());
                parseUser.put("profileFavoriteSport",edtProfileFavoriteSport.getText().toString());
                final ProgressDialog progressDialoggg=new ProgressDialog(getContext());
                progressDialoggg.setMessage("Updating User Information");
                progressDialoggg.show();

                parseUser.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {

                        if(e==null){
                            FancyToast.makeText(getContext(),"Info is Updated", Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                        }else {
                            FancyToast.makeText(getContext(),e.getMessage() ,Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                        }
                        progressDialoggg.dismiss();
                    }
                });

            }
        });
        return view;
    }

}