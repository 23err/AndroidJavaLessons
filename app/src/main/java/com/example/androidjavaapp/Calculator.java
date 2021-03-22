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

    public enum Operation {
        PLUS, MINUS, MUPLTIPLY, DIVIDE, PERCENT, EQUAL, NONE;
    }

    private Operation nextOperation = Operation.NONE;
    private float result = 0;

    private OnChangeResultListener listener;

    public void nextOperation(Operation operation, float number) {

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

        if (operation.equals(Operation.EQUAL)) {

        }

        if (operation != Operation.EQUAL && operation != Operation.PERCENT) {
            nextOperation = operation;
        } else {
            notifyListener();
            nextOperation = Operation.NONE;
            return;
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



}
