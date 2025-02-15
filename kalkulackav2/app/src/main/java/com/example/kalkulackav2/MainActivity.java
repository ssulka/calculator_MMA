package com.example.kalkulackav2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private StringBuilder input;
    private double firstValue = Double.NaN;
    private double secondValue;
    private char currentOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        input = new StringBuilder();
        setupButtons();
    }

    private void setupButtons() {
        int[] numberButtons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10};
        for (int i = 0; i < numberButtons.length; i++) {
            Button btn = findViewById(numberButtons[i]);
            int finalI = i;
            btn.setOnClickListener(v -> appendNumber(finalI));
        }
        findViewById(R.id.buttonRemove).setOnClickListener(v -> delete());
        findViewById(R.id.button11).setOnClickListener(v -> setOperation('+'));
        findViewById(R.id.button12).setOnClickListener(v -> setOperation('-'));
        findViewById(R.id.button14).setOnClickListener(v -> setOperation('*'));
        findViewById(R.id.button15).setOnClickListener(v -> setOperation('/'));
        findViewById(R.id.button16).setOnClickListener(v -> calculateResult());
    }

    private void appendNumber(int number) {
        input.append(number);
        textView.setText(input.toString());
    }

    private void setOperation(char operation) {
        if (!Double.isNaN(firstValue)) {
            calculateResult();
        } else {
            firstValue = Double.parseDouble(input.toString());
        }
        currentOperation = operation;
        input.setLength(0);
    }

    private void calculateResult() {
        if (input.length() == 0 || Double.isNaN(firstValue)) return;
        secondValue = Double.parseDouble(input.toString());

        switch (currentOperation) {
            case '+': firstValue += secondValue; break;
            case '-': firstValue -= secondValue; break;
            case '*': firstValue *= secondValue; break;
            case '/': firstValue = secondValue != 0 ? firstValue / secondValue : 0; break;
        }

        textView.setText(String.valueOf(firstValue));
        input.setLength(0);
    }

    private void delete() {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            textView.setText(input.toString());
        } else {
            firstValue = Double.NaN;
            secondValue = 0;
            currentOperation = '\0';
            textView.setText("0");
        }
    }

}
