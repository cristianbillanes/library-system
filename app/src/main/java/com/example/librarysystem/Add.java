package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public void Add_book(View view){
        Intent intent = new Intent(this, Book_Add.class);
        startActivity(intent);
    }
    public void Add_borrower(View view){
        Intent intent = new Intent(this, Borrower_Add.class);
        startActivity(intent);
    }
    public void Add_borrowedbook(View view){
        Intent intent = new Intent(this, BorrowedBook_Add.class);
        startActivity(intent);
    }
    public void ADD_HOME(View view){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void ADD_VIEW(View view){
        finish();
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}