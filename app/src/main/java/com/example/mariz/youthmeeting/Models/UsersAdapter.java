package com.example.mariz.youthmeeting.Models;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariz.youthmeeting.Activities.UsersProfileActivity;
import com.example.mariz.youthmeeting.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.UsersHolder> {

private List<User> userList;
        Activity activity;

public UsersAdapter ( List <User> userList, Activity activity ) {
        this.activity = activity;
        this.userList = userList;
        }


@Override
public UsersHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( parent.getContext ( ) );
        View view = inflater.inflate ( R.layout.user_item, parent, false );
        UsersHolder usersHolder= new UsersHolder ( view );
        return usersHolder;
        }

@Override
public void onBindViewHolder ( UsersHolder usersHolder, int position ) {
        usersHolder.userDisplayName.setText ( userList.get ( position ).getDisplayName () );
        Picasso.get ().load ( userList.get ( position ).getUserImage () ).into ( usersHolder.userPhoto );
        }

@Override
public int getItemCount ( ) {
        return userList.size ( );
        }

public class UsersHolder extends RecyclerView.ViewHolder {

    ImageView userPhoto;
    public TextView userDisplayName;

    public UsersHolder (  View itemView ) {
        super ( itemView );

        userDisplayName = itemView.findViewById ( R.id.userNameList );
        userPhoto = itemView.findViewById ( R.id.userProfImg );
        userDisplayName.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Intent detintent = new Intent ( activity, UsersProfileActivity.class );
                detintent.putExtra ( "user", userList.get ( getAdapterPosition ( ) ) );
                activity.startActivity ( detintent );
                activity.finish ( );
            }
        } );

    }
}

}
