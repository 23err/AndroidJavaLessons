package com.example.androidjavaapp;


import android.os.Parcel;
import android.os.Parcelable;

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

    enum Operation {
        PLUS, MINUS, MUPLTIPLY, DIVIDE, PERCENT, EQUAL, NONE;
    }

    Operation nextOperation = Operation.NONE;

    private float result = 0;


    private float currentNumber = 0;

    private OnChangeResultListener listener;

    public void nextOperation(Operation operation, float number) {
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
                case PERCENT: {
                    result *= number / 100;
                    break;
                }

                case NONE: {
                    result = number;
                    break;
                }

            }
        }
        if (operation == Operation.EQUAL) {
            notifyListener();
            setNextOperationNone();
        } else {
            nextOperation = operation;
        }
    }

    public void clear() {
        result = 0;
        setNextOperationNone();
        notifyListener();
    }

    private void setNextOperationNone() {
        nextOperation = Operation.NONE;
    }


    public void setOnChangeResultListener(OnChangeResultListener listener) {
        this.listener = listener;
    }

    public void notifyListener() {
        if (listener != null) {
            listener.change(result);
        }
    }

    public float getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(float currentNumber) {
        this.currentNumber = currentNumber;
    }


}
