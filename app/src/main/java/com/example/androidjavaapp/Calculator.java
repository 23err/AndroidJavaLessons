package com.example.androidjavaapp;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Calculator implements Parcelable {

    public Calculator() {
    }

    protected Calculator(Parcel in) {
        result = in.readFloat();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(result);
    }

    public enum Operation {
        PLUS("+"),
        MINUS("-"),
        MUPLTIPLY("*"),
        DIVIDE("/"),
        PERCENT("%"),
        EQUAL("="),
        NONE("");

        private String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }


        @NonNull
        @Override
        public String toString() {
            return symbol;
        }
    }

    private Operation nextOperation = Operation.NONE;
    private float result = 0;
    private String displayOperation = "";
    private OnChangeListener onChangeListener;

    public void nextOperation(Operation operation, float number) {

        if ((operation.equals(Operation.DIVIDE) || operation.equals(Operation.MUPLTIPLY))
                && (nextOperation.equals(Operation.PLUS)|| nextOperation.equals(Operation.MINUS))) {
            displayOperation = "(" + displayOperation + getFormatString(number) +")" ;
        } else {
            displayOperation += getFormatString(number);
        }
        displayOperation += operation;
        notifyDisplayOperationListener();

        if (operation.equals(Operation.PERCENT)) {
            switch (nextOperation) {
                case PLUS:
                case MINUS: {
                    number = result * number / 100;
                    break;
                }
                case MUPLTIPLY:
                case DIVIDE: {
                    number = number / 100;
                    break;
                }
            }

        }

        if (nextOperation != null) {
            switch (nextOperation) {
                case PLUS: {
                    result += number;
                    break;
                }
                case MINUS: {
                    result -= number;
                    break;
                }
                case MUPLTIPLY: {
                    result *= number;
                    break;
                }
                case DIVIDE: {
                    result /= number;
                    break;
                }
                case NONE: {
                    result = number;
                    break;
                }

            }
        }

        if (operation != Operation.EQUAL && operation != Operation.PERCENT) {
            nextOperation = operation;
        } else {
            notifyResultListener();
            nextOperation = Operation.NONE;
            clearDisplayOperation();
            return;
        }
    }

    private String getFormatString(float number) {
        if (number % 1 == 0) {
            return String.valueOf((int) number);
        } else {
            return String.valueOf(number);
        }

    }

    public void clear() {
        result = 0;
        clearDisplayOperation();
        setNextOperationNone();
        notifyResultListener();
        clearDisplayOperation();
        notifyDisplayOperationListener();
    }

    private void setNextOperationNone() {
        nextOperation = Operation.NONE;
    }

    private void clearDisplayOperation() {
        displayOperation = "";
    }

    public void setOnChangeResultListener(OnChangeListener listener) {
        this.onChangeListener = listener;
    }

    public void notifyResultListener() {
        if (onChangeListener != null) {
            onChangeListener.changeResult(result);
        }
    }

    public void notifyDisplayOperationListener() {
        if (onChangeListener != null) {
            onChangeListener.changeDisplayOperation(displayOperation);
        }
    }


}
