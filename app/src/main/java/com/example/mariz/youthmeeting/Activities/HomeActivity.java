package com.example.mariz.youthmeeting.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariz.youthmeeting.Models.Post;
import com.example.mariz.youthmeeting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    String email;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_home );

       // email = getIntent ( ).getExtras ( ).getString ( "currentUser" );

        recyclerView = findViewById(R.id.postView);
        linearLayoutManager = new LinearLayoutManager ( this );
        linearLayoutManager.setReverseLayout ( true );
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager ( linearLayoutManager );


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser ();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()==null){
                  Intent loginIntent = new Intent(HomeActivity.this, MainActivity.class);
                  startActivity(loginIntent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart ( );
        mAuth.addAuthStateListener ( mAuthListener );
        FirebaseRecyclerAdapter <Post, PostViewHolder> FBRA = new FirebaseRecyclerAdapter<Post, PostViewHolder> (
                Post.class,
                R.layout.post_item,
                PostViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder ( PostViewHolder viewHolder, Post model, int position ) {
                final String post_key = getRef ( position ).getKey ( );
                viewHolder.setDesc ( model.getDesc ( ) );
                viewHolder.setImageUrl ( getApplicationContext ( ), model.getImageUrl ( ) );
                viewHolder.setUserPhoto ( getApplicationContext (), model.getUserPhoto () );
                viewHolder.setUserName ( model.getUsername ( ) );
                viewHolder.mView.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick ( View view ) {
                        Intent singleActivity = new Intent ( HomeActivity.this, SinglePostActivity.class );
                        singleActivity.putExtra ( "PostID", post_key );
                        startActivity ( singleActivity );
                    }
                } );
            }
        };
        recyclerView.setAdapter ( FBRA );
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public PostViewHolder ( View itemView ) {
            super ( itemView );
            mView = itemView;
        }

        public void setDesc ( String desc ) {
            TextView post_desc = mView.findViewById ( R.id.postText );
            post_desc.setText ( desc );
        }

        public void setImageUrl ( Context ctx, String imageUrl ) {
            ImageView post_image = mView.findViewById ( R.id.postPhoto );
            Picasso.get ( ).load ( imageUrl ).into ( post_image );
        }

        public void setUserPhoto ( Context ctx, String userPhoto ) {
            ImageView post_image = mView.findViewById ( R.id.userProfImg );
            Picasso.get ( ).load ( userPhoto ).into ( post_image );
        }

        public void setUserName ( String userName ) {
            TextView postUserName = mView.findViewById ( R.id.cUserName );
            postUserName.setText ( userName );
        }

    }

    public void friends ( View view ) {
        DatabaseReference mref = FirebaseDatabase.getInstance ().getReference ( "Users" ).child ( "emailAddress" );
       mref.addValueEventListener ( new ValueEventListener ( ) {
           @Override
           public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
               email = mAuth.getCurrentUser ().getEmail ();
               if ( email.equals ( "bavliarmanious@gmail.com" ) ||
                       email.equals ( "ynabil66@gamil.com" ) ||
                       email.equals ( "jetadros94@gmail.com" ) ||
                       email.equals ( "mera_farhan@hotmail.com" ) ||
                       email.equals ( "miriamalfons1996@gmail.com" ) ||
                       email.equals ( "sallyemad88.se@gmail.com" ) ||
                       email.equals ( "amirramzy@hotmail.com" ) ||
                       email.equals ( "jacktadros@gmail.com" ) ||
                       email.equals ( "annouj@gmail.com" ) ||
                       email.equals ( "gwanas20@yahoo.com" ) ||
                       email.equals ( "bishoymedhat92@hotmail.com" ) ||
                       email.equals ( "amgad_emil@hotmail.com" ) ||
                       email.equals ( "markos550@yahoo.com" ) ||
                       email.equals ( "alberdawoud@yahoo.com" ) ||
                       email.equals ( "monamounir92@gmail.com" ) ||
                       email.equals ( "nardeen.georges@gmail.com" ) ||
                       email.equals ( "tamernabil7@yahoo.com" ) ||
                       email.equals ( "godgifts9112018@gmail.com" ) ||
                       email.equals ( "naeim222@gmail.com" ) ||
                       email.equals ( "eleon.trade@gmail.com" ) ||
                       email.equals ( "christin.atef26.ca@gmail.com" ) ||
                       email.equals ( "rambo.talaat@gmail.com" ) ||
                       email.equals ( "erinymansy@hotmail.com" ) ||
                       email.equals ( "nabil.nesreen@gmail.com" ) ||
                       email.equals ( "silviayehia@yahoo.com" ) ||
                       email.equals ( "gosianwadiaa@yahoo.com" ) ||
                       email.equals ( "rashid.raafat@gmail.com" ) ||
                       email.equals ( "marco.lotfy@yahoo.com" ) ||
                       email.equals ( "vina25201142@gmail.com" ) ||
                       email.equals ( "andrew_nagy189@yahoo.com" ) ||
                       email.equals ( "marizadly@hotmail.com" ) ) {
                   Intent intent = new Intent ( HomeActivity.this, ListActivity.class );
                   startActivity ( intent );
               } else {
                   Intent intent = new Intent ( HomeActivity.this, UsersListActivity.class );
                   startActivity ( intent );
               }
           }

           @Override
           public void onCancelled ( @NonNull DatabaseError databaseError ) {

           }
       } );

    }

    public void addPost ( View view ) {
        Intent intent = new Intent (HomeActivity.this, AddPostActivity.class);
        startActivity(intent);
    }

    public void signOut ( View view ) {
        mAuth.signOut();
        Intent logouIntent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(logouIntent);
    }
}
