package com.example.mariz.youthmeeting.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariz.youthmeeting.R;
import com.squareup.picasso.Picasso;

public class WelcomeActivity extends AppCompatActivity {
    ImageView logo;
    TextView title;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_welcome );



            logo = findViewById ( R.id.logo );
            Picasso.get ().load ( R.drawable.icon ).into ( logo );
            title = findViewById ( R.id.textView2 );
            new Handler ().postDelayed( new Runnable() {


                @Override
                public void run() {
                    Intent homeIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT );
        }
    }

