package com.example.eatmeet.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.eatmeet.R;

/**
 * Created by media on 21/06/2016.
 */
public class SignInActivity extends AppCompatActivity{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View v){
        //fai login cn db

        String mail=((EditText)this.findViewById(R.id.etUserName)).getText().toString();
        String pass=((EditText)this.findViewById(R.id.etPass)).getText().toString();

    }
}
