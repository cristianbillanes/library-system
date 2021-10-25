package com.example.librarysystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    TextView email,id,name;
    SharedPreferences_Class sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.profile_email);
        name = findViewById(R.id.profile_name);
        id = findViewById(R.id.profile_id);
        SessionChanges();
    }
    private void SessionChanges(){
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        String v_name = user.get(sessionManager.NAME);
        String v_email = user.get(sessionManager.EMAIL);
        String v_id = user.get(sessionManager.ID);
        email.setText(v_email);
        name.setText(v_name);
        id.setText(v_id);
    }
    public void log_out(View view){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you will lou-out");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("Log_out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                sessionManager.logout();
            }
        });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
    public void back_to_home_page(View view){
        finish();
    }
    public void Edit_Profile(View view){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SessionChanges();
    }
}