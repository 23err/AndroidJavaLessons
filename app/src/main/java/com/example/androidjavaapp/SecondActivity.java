package com.example.androidjavaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView dateTV;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViews();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateTV.setText(String.format("%d.%d.%d",i2,i1,i));
            }
        });

    }

    private void findViews() {
        dateTV = findViewById(R.id.dateET);
        calendarView = findViewById(R.id.calendarView);
    }
}