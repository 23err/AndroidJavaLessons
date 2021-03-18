package com.example.androidjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        btn.setOnClickListener(e->{
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });

    }

    private void findViews() {
        btn = findViewById(R.id.btn);
    }
}