package com.example.androidjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

    }

    private void getViews() {
        tvResult = findViewById(R.id.tvResult);
    }

    public void btnNumbersOnClick(View btn) {
        if (btn.getId() == R.id.btnDot && tvResult.getText().toString().contains(getResources().getString(R.string.dot))) {
            return;
        }
        if (tvResult.getText().toString().equals(getResources().getString(R.string._0))
                && btn.getId() != R.id.btnDot) {
            tvResult.setText(null);
        }


        tvResult.setText(tvResult.getText().toString() + ((Button) btn).getText().toString());
    }

    public void btnClearOnClick(View view) {
        tvResult.setText(String.valueOf(0));
    }

    public void btnPlusMinusOnClick(View view) {
        if (!tvResult.getText().toString().contains(getResources().getString(R.string.minus))) {
            tvResult.setText(getResources().getString(R.string.minus) + tvResult.getText().toString());
        } else {
            tvResult.setText(tvResult.getText().toString().substring(1));
        }
    }
}