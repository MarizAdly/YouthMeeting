package com.example.mariz.youthmeeting.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariz.youthmeeting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    ImageView logo;
    EditText emailEdit, passwordEdit;
    CheckBox saveLoginCheckBox;
    Button signIn;
    TextView forget, signUpp;

    DatabaseReference mDatabase;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        logo = findViewById ( R.id.logo );

        Picasso.get ( ).load ( R.drawable.icon ).into ( logo );
        emailEdit = findViewById ( R.id.emailAddEditText );
        passwordEdit = findViewById ( R.id.passWord );
        saveLoginCheckBox = findViewById ( R.id.saveLoginCheckBox );
        signIn = findViewById ( R.id.signIn );
        signUpp = findViewById ( R.id.signUpp );
        forget = findViewById ( R.id.forget );
        loginPreferences = getSharedPreferences ( "loginPrefs", MODE_PRIVATE );
        loginPrefsEditor = loginPreferences.edit ( );


        mAuth = FirebaseAuth.getInstance ( );

        mDatabase = FirebaseDatabase.getInstance ( ).getReference ( ).child ( "Users" );
    }


    public void savePass ( View view ) {
        saveLogin = loginPreferences.getBoolean ( "saveLogin", false );
        if ( saveLogin == true ) {
            emailEdit.setText ( loginPreferences.getString ( "email", "" ) );
            passwordEdit.setText ( loginPreferences.getString ( "password", "" ) );
            saveLoginCheckBox.setChecked ( true );

        }
        InputMethodManager imm = ( InputMethodManager ) getSystemService ( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow ( emailEdit.getWindowToken ( ), 0 );

        String Email = emailEdit.getText ( ).toString ( );
        String Password = passwordEdit.getText ( ).toString ( );


        if ( saveLoginCheckBox.isChecked ( ) ) {
            loginPrefsEditor.putBoolean ( "saveLogin", true );
            loginPrefsEditor.putString ( "email", Email );
            loginPrefsEditor.putString ( "password", Password );

            loginPrefsEditor.commit ( );
        } else {
            loginPrefsEditor.commit ( );
        }
    }

    public void logIn ( View view ) {
        final String email = emailEdit.getText ().toString ();
        String password = passwordEdit.getText ().toString ();

        if ( TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter Your Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter Your Password!", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword ( email, password ).addOnCompleteListener ( new OnCompleteListener <AuthResult> ( ) {
            @Override
            public void onComplete ( @NonNull Task <AuthResult> task ) {
                if ( task.isSuccessful ( ) ) {

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
                            email.equals ( "marizadly@hotmail.com" )) {

                        Intent intent = new Intent ( MainActivity.this, ListActivity.class);
                        intent.putExtra ( "currentUser", email );
                        startActivity ( intent );
                    }
                    else {
                        Intent intent = new Intent ( MainActivity.this,UsersListActivity.class);

                        startActivity ( intent );
                    }




                } else {
                    Toast.makeText ( MainActivity.this, "Couldn't login, User not found", Toast.LENGTH_SHORT ).show ( );
                }
            }
        } );
    }
            public void signUp ( View view ) {
                Intent intent = new Intent ( MainActivity.this, RegisterActivity.class );
                startActivity ( intent );
                finish ( );
            }


            public void forget ( View view ) {

                Intent passIntent = new Intent ( MainActivity.this, ForgetPassActivity.class );
                startActivity ( passIntent );
                finish ( );
            }

}
