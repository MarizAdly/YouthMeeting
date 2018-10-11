package com.example.mariz.youthmeeting.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariz.youthmeeting.Models.ListAdapter;
import com.example.mariz.youthmeeting.Models.Post;
import com.example.mariz.youthmeeting.Models.User;
import com.example.mariz.youthmeeting.Models.UsersAdapter;
import com.example.mariz.youthmeeting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recView;
   ListAdapter usersAdapter;
    List<User> userList;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_list );


        userList = new ArrayList<> ( );

        recView = findViewById ( R.id.usersview);


        FirebaseDatabase database = FirebaseDatabase.getInstance ( );
        final Query myRef = database.getReference ( "Users" ).orderByChild ( "displayName" );
        myRef.addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                for (DataSnapshot userSnapShot : dataSnapshot.getChildren ( )) {
                    User user = userSnapShot.getValue ( User.class );
                    userList.add ( user );
                    usersAdapter.notifyDataSetChanged ( );
                }

            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {
                Toast.makeText ( ListActivity.this, databaseError.getMessage ( ), Toast.LENGTH_LONG ).show ( );
            }

        } );


        usersAdapter = new ListAdapter ( userList, this );
        recView.setAdapter ( usersAdapter );
        recView.setLayoutManager ( new LinearLayoutManager ( this ) );



    }






    public void Home ( View view ) {
        Intent intent = new Intent (ListActivity.this, HomeActivity.class);
        startActivity ( intent );
    }
}
