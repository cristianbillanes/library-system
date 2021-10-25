package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class Home extends AppCompatActivity {
    ImageView btnProfile;
    SharedPreferences_Class sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnProfile = findViewById(R.id.home_profile_button);
        sessionManager = new SharedPreferences_Class(this);
        sessionManager.checkLogin();
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile();
            }
        });

    }
    private void Profile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void HOME_ADD_BOOK(View view){
        finish();
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }
    public void HOME_BOOK_VIEW(View view){
        finish();
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }


}