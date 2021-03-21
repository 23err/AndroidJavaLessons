package com.example.androidjavaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String CALCULATOR = "calculator";
    private Button btn;
    private TextView tvResult;
    Calculator calculator;
    private boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        if (savedInstanceState != null) {
            calculator = (Calculator) savedInstanceState.getParcelable(CALCULATOR);
            showCorrectFloatResult(calculator.getCurrentNumber());
        } else {
            calculator = new Calculator();
        }

        calculator.setOnChangeResultListener(new OnChangeResultListener() {
            @Override
            public void change(float result) {
                showCorrectFloatResult(result);
            }
        });


    }

    private void showCorrectFloatResult(float number) {
        if (number % 1 == 0) {
            tvResult.setText(String.valueOf((int)number));
        } else{
            tvResult.setText(String.valueOf(number));
        }
    }

    private void getViews() {
        tvResult = findViewById(R.id.tvResult);
    }

    public void btnNumbersOnClick(View btn) {
        checkNewInput();
        if (btn.getId() == R.id.btnDot && tvResult.getText().toString().contains(getResources().getString(R.string.dot))) {
            return;
        }
        if (tvResult.getText().toString().equals(getResources().getString(R.string._0))
                && btn.getId() != R.id.btnDot) {
            tvResult.setText(null);
        }


        tvResult.setText(tvResult.getText().toString() + ((Button) btn).getText().toString());
    }

    private void checkNewInput() {
        if (newInput) {
            newInput = false;
            tvResult.setText(getResources().getString(R.string._0));
        }
    }

    public void btnClearOnClick(View view) {
        tvResult.setText(String.valueOf(getResources().getString(R.string._0)));
    }

    public void btnPlusMinusOnClick(View view) {
        checkNewInput();
        if (!tvResult.getText().toString().contains(getResources().getString(R.string.minus))) {
            tvResult.setText(getResources().getString(R.string.minus) + tvResult.getText().toString());
        } else {
            tvResult.setText(tvResult.getText().toString().substring(1));
        }
    }

    public void btnOperationOnClick(View view) throws Exception {
        newInput = true;
        Calculator.Operation nextOperation;
        switch (view.getId()) {
            case (R.id.btnPlus): {
                nextOperation = Calculator.Operation.PLUS;
                break;
            }
            case (R.id.btnMinus): {
                nextOperation = Calculator.Operation.MINUS;
                break;
            }
            case (R.id.btnMultiply): {
                nextOperation = Calculator.Operation.MUPLTIPLY;
                break;
            }
            case (R.id.btnDivide): {
                nextOperation = Calculator.Operation.DIVIDE;
                break;
            }
            case (R.id.btnPercent): {
                nextOperation = Calculator.Operation.PERCENT;
                break;
            }
            case (R.id.btnEqual): {
                nextOperation = Calculator.Operation.EQUAL;
                break;
            }
            default:
                throw new Exception("Для этой кнопки не назначено действие");

        }

        float number = Float.parseFloat(tvResult.getText().toString());
        calculator.nextOperation(nextOperation, number);

    }

    public void btnClearAllOnClick(View view) {
        calculator.clear();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        float currentNumber = Float.parseFloat(tvResult.getText().toString());
        calculator.setCurrentNumber(currentNumber);
        outState.putParcelable(CALCULATOR, calculator);
    }
}