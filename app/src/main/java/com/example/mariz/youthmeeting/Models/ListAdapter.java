package com.example.mariz.youthmeeting.Models;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariz.youthmeeting.Activities.ProfileActivity;
import com.example.mariz.youthmeeting.Activities.UsersProfileActivity;
import com.example.mariz.youthmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

         List<User> userList;
        Activity activity;

public ListAdapter ( List <User> userList, Activity activity ) {
        this.activity = activity;
        this.userList = userList;
        }


@Override
public ListHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( parent.getContext ( ) );
        View view = inflater.inflate ( R.layout.useritem, parent, false );
       ListHolder listHolder= new ListHolder ( view );
        return listHolder;
        }

@Override
public void onBindViewHolder ( ListHolder listHolder, int position ) {
        listHolder.userDisplayName.setText ( userList.get ( position ).getDisplayName () );
        Picasso.get ().load ( userList.get ( position ).getUserImage () ).into ( listHolder.userPhoto );
        }

@Override
public int getItemCount ( ) {
        return userList.size ( );
        }

public class ListHolder extends RecyclerView.ViewHolder {

    ImageView userPhoto;
    TextView userDisplayName;


    public ListHolder (  View itemView ) {
        super ( itemView );


        userDisplayName = itemView.findViewById ( R.id.usernamelist );
        userPhoto = itemView.findViewById ( R.id.userprofile );
        userDisplayName.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {

                    Intent detintent = new Intent ( activity, ProfileActivity.class );
                    detintent.putExtra ( "User", userList.get ( getAdapterPosition ( ) ) );
                    activity.startActivity ( detintent );
                    activity.finish ( );
                }
        } );

    }
}


}
