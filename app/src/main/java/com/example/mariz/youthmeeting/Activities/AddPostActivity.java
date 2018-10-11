package com.example.mariz.youthmeeting.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariz.youthmeeting.Models.Post;
import com.example.mariz.youthmeeting.Models.User;
import com.example.mariz.youthmeeting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPostActivity extends AppCompatActivity {


    Post post;
    User user;

    Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 2;
    ImageView postImage;
    TextView title;
    EditText postText;
    ImageButton camera, addpost, cancel;

    private StorageReference storage;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_post );

        post = new Post ( );
        user = new User ( );


        title = findViewById ( R.id.titleView );
        postText = findViewById ( R.id.postText );
        postImage = findViewById ( R.id.postImage );
        camera = findViewById ( R.id.addPhoto );
        addpost = findViewById ( R.id.addPost );
        cancel = findViewById ( R.id.cancel );

        storage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
        database = FirebaseDatabase.getInstance ();
        databaseRef = database.getReference ( "Posts" );
    }

    public void addPostImage ( View view ) {
        Intent intent = new Intent ( );
        intent.setType ( "image/*" );
        intent.setAction ( Intent.ACTION_GET_CONTENT );
        startActivityForResult ( Intent.createChooser ( intent, "Select Picture" ), GALLERY_REQUEST_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            imageUri = data.getData();
            postImage.setImageURI(imageUri);
        }
    }

    public void addPost ( View view ) {

        Toast.makeText(AddPostActivity.this, "POSTING...", Toast.LENGTH_LONG).show();

        final String PostDesc = postText.getText().toString().trim();

        if (!TextUtils.isEmpty(PostDesc) ){
            final StorageReference filepath = storage.child("post_images").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl ( ).addOnSuccessListener ( new OnSuccessListener <Uri> ( ) {
                        @Override
                        public void onSuccess ( final Uri uri ) {

                     Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();

                    final DatabaseReference newPost = databaseRef.push();
                    mDatabaseUsers.addValueEventListener(new ValueEventListener () {
                        @Override
                        public void onDataChange ( final DataSnapshot dataSnapshot ) {
                         post.setDesc ( PostDesc );
                         post.setImageUrl ( uri.toString () );
                         post.setUid ( mCurrentUser.getUid () );
                         post.setUsername ( dataSnapshot.child ( "displayName" ).getValue ( ).toString () );
                         post.setUserPhoto ( dataSnapshot.child ( "userImage" ).getValue ().toString () );

                         newPost.child ( "username" ).setValue ( dataSnapshot.child ( "displayName" ).getValue ())
                                 .addOnCompleteListener ( new OnCompleteListener <Void> ( ) {
                                     @Override
                                     public void onComplete ( @NonNull Task <Void> task ) {
                                         if (task.isSuccessful ()){
                                             newPost.setValue ( post );
                                             Toast.makeText ( AddPostActivity.this, "Post added", Toast.LENGTH_SHORT ).show ( );
                                             Intent regIntent = new Intent ( AddPostActivity.this, HomeActivity.class );
                                             startActivity ( regIntent );
                                         }
                                     }
                                 } );

                        }

                        @Override
                        public void onCancelled ( @NonNull DatabaseError databaseError ) {
                            Toast.makeText(getApplicationContext(), "Uploading failed" + databaseError.getMessage (), Toast.LENGTH_SHORT).show();

                        }
                    });
                        }

                    });
                }
            });
        }
    }




    public void cancelPost ( View view ) {
        Intent intent = new Intent ( AddPostActivity.this, HomeActivity.class );
        startActivity ( intent );
    }


}
