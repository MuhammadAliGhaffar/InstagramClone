package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class UsersTab extends Fragment implements AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList mArrayList;
    private ArrayAdapter mArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView=view.findViewById(R.id.listView);
        mArrayList =new ArrayList();
        mArrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mArrayList);

        //listView.setOnClickListener();
        listView.setOnItemLongClickListener(UsersTab.this);


        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialoggg=new ProgressDialog(getContext());
        progressDialoggg.setMessage("Please wait loading...");
        progressDialoggg.setCanceledOnTouchOutside(false);
        progressDialoggg.show();
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){

                    if(objects.size() > 0){
                        for (ParseUser user : objects){
                            mArrayList.add(user.getUsername());
                        }
                        listView.setAdapter(mArrayAdapter);
                    }

                }
                progressDialoggg.dismiss();
            }
        });


        return view;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

        ParseQuery<ParseUser> parseQuery =ParseUser.getQuery();
        parseQuery.whereEqualTo("username",mArrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null && e==null){
                    final PrettyDialog prettyDialog=new PrettyDialog(getContext());
                    prettyDialog.setTitle(user.getUsername()+" 's Info")
                            .setMessage(user.get("profileName") + "\n"
                                    +"Bio : "+user.get("profileBio") + "\n"
                                    +"Profession : "+user.get("profileProfession") + "\n"
                                    +"Hobbies : "+user.get("profileHobbies") + "\n"
                                    +"Favorite Sport : "+user.get("profileFavoriteSport")


                            ).setIcon(R.drawable.user)
                            .addButton(
                                    "Ok",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_blue,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            prettyDialog.dismiss();
                                        }
                                    }

                            ).show();



                }
            }
        });
        return true;
    }
}