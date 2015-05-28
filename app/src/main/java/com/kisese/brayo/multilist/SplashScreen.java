package com.kisese.brayo.multilist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Brayo on 4/27/2015.
 */
public class SplashScreen extends ActionBarActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.splash_screen);

        logo = (ImageView)findViewById(R.id.logo_home_top);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        logo.startAnimation(animation);



        Thread backgroud = new Thread(){
            public void run(){

                try{
                    sleep(3*500);
                    Intent i = new Intent(getApplicationContext(), MainLoginActivity.class);

                    startActivity(i);
                    finish();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        backgroud.start();
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
