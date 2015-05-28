package com.kisese.brayo.multilist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by Brayo on 4/27/2015.
 */
public class MainLoginActivity extends ActionBarActivity{

    BootstrapButton login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.main_login);


        login = (BootstrapButton)findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLoginActivity.this, MainMenuActivity.class);
                MainLoginActivity.this.startActivity(intent);
            }
        });

        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
