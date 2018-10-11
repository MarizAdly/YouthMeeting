package com.example.mariz.youthmeeting.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariz.youthmeeting.Models.User;
import com.example.mariz.youthmeeting.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends AppCompatActivity {
    TextView logIn;
    TextView titleView, uploadView;
    ImageView profileView;
    EditText userNameEdit, emailAddEdit, phoneNoEdit, passWordEdit, bdEdit, addressEdit;
    Button homeLocation, signup;

    private static final int PICK_IMAGE = 100;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;


    User user;
    Uri imageUri;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        logIn = findViewById ( R.id.logIn );
        titleView = findViewById ( R.id.titleView );
        uploadView = findViewById ( R.id.uploadView );

        profileView = findViewById ( R.id.profileView );

        userNameEdit = findViewById ( R.id.userNameEdit );
        emailAddEdit = findViewById ( R.id.emailAddEdit );
        phoneNoEdit = findViewById ( R.id.phoneNoEdit );
        passWordEdit = findViewById ( R.id.passWordEdit );
        bdEdit = findViewById ( R.id.bdEdit );
        addressEdit = findViewById ( R.id.addressEdit );
        homeLocation = findViewById ( R.id.homeLocation );
        signup = findViewById ( R.id.signup );

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient ( this );
        user = new User ( );

        storage = FirebaseStorage.getInstance ( );
        storageReference = storage.getReference ( );
        mAuth = FirebaseAuth.getInstance ( );
        mDatabase = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "Users" );


    }

    public void addImg ( View view ) {
        openGallery ( );
    }

    private void openGallery ( ) {
        Intent intent = new Intent ( );
        intent.setType ( "image/*" );
        intent.setAction ( Intent.ACTION_GET_CONTENT );
        startActivityForResult ( Intent.createChooser ( intent, "Select Picture" ), PICK_IMAGE );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        if ( requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {

            Uri imageUri = data.getData ( );
            CropImage.activity ( imageUri )
                    .setGuidelines ( CropImageView.Guidelines.ON )
                    .setAspectRatio ( 1, 1 )
                    .start ( this );
        }
        if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result = CropImage.getActivityResult ( data );
            if ( resultCode == RESULT_OK ) {
                imageUri = result.getUri ( );
                profileView.setImageURI ( imageUri );
            } else {
                if ( requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE ) {
                    Exception err = result.getError ( );
                }
            }
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission ( ) {
        if ( ContextCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED ) {

            if ( ActivityCompat.shouldShowRequestPermissionRationale ( this, Manifest.permission.ACCESS_FINE_LOCATION ) ) {

                new AlertDialog.Builder ( this ).setTitle ( "App Permission to location is needed" )
                        .setMessage ( "App need to access Location" )
                        .setPositiveButton ( "ok", new DialogInterface.OnClickListener ( ) {
                            @Override
                            public void onClick ( DialogInterface dialogInterface, int i ) {

                                ActivityCompat.requestPermissions ( RegisterActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        } )
                        .create ( )
                        .show ( );


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }


    private FusedLocationProviderClient mFusedLocationClient;
    double latLoc;
    double lngLoc;

    public void gettingLoc ( ) {

        if ( ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        mFusedLocationClient.getLastLocation ( )
                .addOnSuccessListener ( this, new OnSuccessListener <Location> ( ) {
                    @Override
                    public void onSuccess ( Location location ) {
                        // Got last known location. In some rare situations this can be null.
                        if ( location != null ) {
                            // Logic to handle location object
                            Log.d ( "dfsdf", String.valueOf ( location.getLatitude ( ) ) );
                            latLoc = location.getLatitude ( );
                            lngLoc = location.getLongitude ( );

                        }
                    }
                } );
    }


    public void getLocation ( View view ) {
        if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if ( ContextCompat.checkSelfPermission ( getApplicationContext ( ),
                    Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED ) {
                //Location Permission already granted

                gettingLoc ( );
                Toast.makeText ( RegisterActivity.this, "Location is saved!", Toast.LENGTH_LONG ).show ( );

            } else {
                //Request Location Permission
                checkLocationPermission ( );
            }
        } else {

            gettingLoc ( );

        }
    }


    public void signup ( View view ) {
        final String fullname = userNameEdit.getText ( ).toString ( );
        final String emaillAdd = emailAddEdit.getText ( ).toString ( );
        final String phone = phoneNoEdit.getText ( ).toString ( );
        final String address = addressEdit.getText ( ).toString ( );
        final String birthday = bdEdit.getText ( ).toString ( );
        final String password = passWordEdit.getText ( ).toString ( );

        if ( TextUtils.isEmpty ( fullname ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Your Name!", Toast.LENGTH_SHORT ).show ( );
            return;
        }
        if ( TextUtils.isEmpty ( password ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Valid Passwords!", Toast.LENGTH_SHORT ).show ( );
            return;
        }

        if ( TextUtils.isEmpty ( emaillAdd ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Your Email Address!", Toast.LENGTH_SHORT ).show ( );
            return;
        }
        if ( TextUtils.isEmpty ( phone ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Your Phone Number!", Toast.LENGTH_SHORT ).show ( );
            return;
        }

        if ( TextUtils.isEmpty ( address ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Your Home Address!", Toast.LENGTH_SHORT ).show ( );
            return;
        }

        if ( TextUtils.isEmpty ( birthday ) ) {
            Toast.makeText ( getApplicationContext ( ), "Enter Your Birth Date!", Toast.LENGTH_SHORT ).show ( );
            return;
        }

        mAuth.createUserWithEmailAndPassword ( emaillAdd, password )
                .addOnCompleteListener ( new OnCompleteListener <AuthResult> ( ) {
                    @Override
                    public void onComplete ( @NonNull Task <AuthResult> task ) {

                        if ( imageUri != null ) {
                            final ProgressDialog progressDialog = new ProgressDialog ( RegisterActivity.this );
                            progressDialog.setTitle ( "Uploading..." );
                            progressDialog.show ( );

                            final StorageReference ref = storageReference.child ( "images/" ).child ( imageUri.getLastPathSegment ( ) );
                            ref.putFile ( imageUri )
                                    .addOnSuccessListener ( new OnSuccessListener <UploadTask.TaskSnapshot> ( ) {
                                        @Override
                                        public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot ) {
                                            progressDialog.dismiss ( );
                                            ref.getDownloadUrl ( ).addOnSuccessListener ( new OnSuccessListener <Uri> ( ) {
                                                @Override
                                                public void onSuccess ( Uri uri ) {
                                                    String user_id = mAuth.getCurrentUser ( ).getUid ( );
                                                    DatabaseReference mRef = mDatabase.child ( user_id );
                                                    user.setAddress ( address );
                                                    user.setBirthDate ( birthday );
                                                    user.setDisplayName ( fullname );
                                                    user.setEmailAddress ( emaillAdd );
                                                    user.setPhoneNo ( phone );
                                                    user.setUserImage ( uri.toString () );
                                                    user.setUlng ( lngLoc );
                                                    user.setuLat ( latLoc );

                                                    mRef.setValue ( user );

                                                    Toast.makeText ( RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT ).show ( );
                                                    Intent regIntent = new Intent ( RegisterActivity.this, MainActivity.class );
                                                    regIntent.putExtra ( "User", user );
                                                    startActivity ( regIntent );
                                                }
                                            } );
                                        }
                                    } )
                                    .addOnFailureListener ( new OnFailureListener ( ) {
                                        @Override
                                        public void onFailure ( @NonNull Exception e ) {
                                            progressDialog.dismiss ( );
                                            Toast.makeText ( RegisterActivity.this, e.getMessage ( ), Toast.LENGTH_LONG ).show ( );
                                        }
                                    } )
                                    .addOnProgressListener ( new OnProgressListener <UploadTask.TaskSnapshot> ( ) {
                                        @Override
                                        public void onProgress ( UploadTask.TaskSnapshot taskSnapshot ) {
                                            double progress = ( 100.0 * taskSnapshot.getBytesTransferred ( ) / taskSnapshot
                                                    .getTotalByteCount ( ) );
                                            progressDialog.setMessage ( "Uploaded " + ( int ) progress + "%" );
                                        }
                                    } );


                        } else

                        {

                            Toast.makeText ( RegisterActivity.this, "Authentication failed." + task.getException ( ).toString ( ),
                                    Toast.LENGTH_SHORT ).show ( );

                        }
                    }


                } );
    }


    public void back ( View view ) {
        Intent intent = new Intent (RegisterActivity.this, MainActivity.class);
        startActivity ( intent );
    }
}

