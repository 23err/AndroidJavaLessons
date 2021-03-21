package com.example.androidjavaapp;


public class Calculator {

    enum Operation {
        PLUS, MINUS, MUPLTIPLY, DIVIDE, PERCENT, EQUAL;
    }

    Operation nextOperation = null;

    float result = 0;

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
                    result %= number;
                }

            }
        }
        if (operation == Operation.EQUAL) {
            notifyListener();
            nextOperation = null;
        } else {
            nextOperation = operation;
        }
    }

    public void clear() {
        result = 0;
        notifyListener();
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
