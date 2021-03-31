package com.example.androidjavaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    public static final String CALCULATOR = "calculator";
    public static final String CURRENT_NUMBER = "current_number";
    public static final int REQUEST_CODE_SETTING = 7777;
    private Button btn;
    private TextView tvResult;
    private Calculator calculator;
    private boolean isNewInput = true;
    private TextView tvOperations;
    private boolean isNewCalculation;
    private ImageButton btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();

        if (savedInstanceState != null) {
            calculator = (Calculator) savedInstanceState.getParcelable(CALCULATOR);
            showCorrectFloatResult(savedInstanceState.getFloat(CURRENT_NUMBER));

        } else {
            calculator = new Calculator();
        }

        setChangeListener();

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING) {
            recreate();
        }
    }

    private void setChangeListener() {
        calculator.setOnChangeResultListener(new OnChangeListener() {
            @Override
            public void changeResult(float result) {
                showCorrectFloatResult(result);
            }

            @Override
            public void changeDisplayOperation(String displayOperation) {
                tvOperations.setText(displayOperation);
            }
        });

        calculator.notifyDisplayOperationListener();
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
        tvOperations = findViewById(R.id.tvOperations);
        btnSetting = findViewById(R.id.btnSetting);


    }

    public void btnNumbersOnClick(View btn) {
        checkNewInput();
        String tvResultText = tvResult.getText().toString();
        if (btn.getId() == R.id.btnDot && tvResultText.contains(getResources().getString(R.string.dot))) {
            return;
        }
        if (tvResultText.equals(getResources().getString(R.string._0))
                && btn.getId() != R.id.btnDot) {
            tvResultText = "";
        }


        tvResult.setText(tvResultText + ((Button) btn).getText().toString());
    }

    private void checkNewInput() {
        if (isNewInput) {
            isNewInput = false;
            tvResult.setText(getResources().getString(R.string._0));
        }
    }



    public void btnClearOnClick(View view) {
        tvResult.setText(String.valueOf(getResources().getString(R.string._0)));
    }

    public void btnPlusMinusOnClick(View view) {
        checkNewInput();
        String tvResultText = tvResult.getText().toString();
        if (tvResultText.equals(getResources().getString(R.string._0))){
            return;
        } else if (!tvResultText.contains(getResources().getString(R.string.minus))) {
            tvResult.setText(getResources().getString(R.string.minus) + tvResultText);
        } else {
            tvResult.setText(tvResultText.substring(1));
        }
    }

    public void btnOperationOnClick(View view) throws Exception {
        isNewInput = true;
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
                isNewCalculation = true;

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
        outState.putFloat(CURRENT_NUMBER, currentNumber);
        outState.putParcelable(CALCULATOR, calculator);
    }
}