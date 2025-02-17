package com.example.kalkulackav2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String input = "";
    private String operator = "";
    private double firstNumber = 0;
    private double secondNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Tlačidlá pre čísla
        findViewById(R.id.button1).setOnClickListener(view -> inputNumber("1"));
        findViewById(R.id.button2).setOnClickListener(view -> inputNumber("2"));
        findViewById(R.id.button3).setOnClickListener(view -> inputNumber("3"));
        findViewById(R.id.button4).setOnClickListener(view -> inputNumber("4"));
        findViewById(R.id.button5).setOnClickListener(view -> inputNumber("5"));
        findViewById(R.id.button6).setOnClickListener(view -> inputNumber("6"));
        findViewById(R.id.button7).setOnClickListener(view -> inputNumber("7"));
        findViewById(R.id.button8).setOnClickListener(view -> inputNumber("8"));
        findViewById(R.id.button9).setOnClickListener(view -> inputNumber("9"));
        findViewById(R.id.button0).setOnClickListener(view -> inputNumber("0"));

        // Tlačidlá pre operácie
        findViewById(R.id.buttonAdd).setOnClickListener(view -> setOperator("+"));
        findViewById(R.id.buttonSubtract).setOnClickListener(view -> setOperator("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(view -> setOperator("*"));
        findViewById(R.id.buttonDivide).setOnClickListener(view -> setOperator("/"));

        // Tlačidlá pre vymazanie a rovná sa
        findViewById(R.id.buttonClear).setOnClickListener(view -> clear());
        findViewById(R.id.buttonEquals).setOnClickListener(view -> calculate());
    }

    private void inputNumber(String num) {
        input += num;
        display.setText(input);
    }

    private void setOperator(String op) {
        if (!input.isEmpty()) {
            firstNumber = Double.parseDouble(input);
            input = "";
            operator = op;
        }
    }

    private void clear() {
        input = "";
        operator = "";
        display.setText("0");
    }

    private void calculate() {
        if (!input.isEmpty()) {
            secondNumber = Double.parseDouble(input);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result));
            input = String.valueOf(result);
        }
    }
}
