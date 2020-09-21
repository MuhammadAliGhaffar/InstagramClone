package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersTab extends Fragment {

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

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialoggg=new ProgressDialog(getContext());
        progressDialoggg.setMessage("Updating all users...");
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
}