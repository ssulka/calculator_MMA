package com.example.kalkulackav2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private StringBuilder inputFormula = new StringBuilder();  // Pre výpočet
    private StringBuilder displayFormula = new StringBuilder();  // Pre zobrazenie na displeji

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        display.setText("0");

        // Číselné tlačidlá
        setupNumberButton(R.id.button0, "0");
        setupNumberButton(R.id.button1, "1");
        setupNumberButton(R.id.button2, "2");
        setupNumberButton(R.id.button3, "3");
        setupNumberButton(R.id.button4, "4");
        setupNumberButton(R.id.button5, "5");
        setupNumberButton(R.id.button6, "6");
        setupNumberButton(R.id.button7, "7");
        setupNumberButton(R.id.button8, "8");
        setupNumberButton(R.id.button9, "9");
        setupNumberButton(R.id.buttonDecimal, ".");

        // Operátory
        setupOperatorButton(R.id.buttonAdd, "+");
        setupOperatorButton(R.id.buttonSubtract, "-");
        setupOperatorButton(R.id.buttonMultiply, "*");
        setupOperatorButton(R.id.buttonDivide, "/");
        setupOperatorButton(R.id.buttonModulo, "%");

        // Zátvorky
        Button buttonLeftBracket = findViewById(R.id.buttonLeftBracket);
        if (buttonLeftBracket != null) {
            buttonLeftBracket.setOnClickListener(v -> {
                inputFormula.append("(");
                displayFormula.append("(");
                updateDisplay();
            });
        }

        Button buttonRightBracket = findViewById(R.id.buttonRightBracket);
        if (buttonRightBracket != null) {
            buttonRightBracket.setOnClickListener(v -> {
                inputFormula.append(")");
                displayFormula.append(")");
                updateDisplay();
            });
        }

        // Konštanty
        setupConstantButton(R.id.buttonPi, "pi", "π");

        // Funkcie
        setupFunctionButton(R.id.buttonSin, "sin", "sin");
        setupFunctionButton(R.id.buttonCos, "cos", "cos");
        setupFunctionButton(R.id.buttonTan, "tan", "tan");
        setupFunctionButton(R.id.buttonLog, "log10", "log");
        setupFunctionButton(R.id.buttonLn, "ln", "ln");

        Button buttonPower2 = findViewById(R.id.buttonPower2);
        if (buttonPower2 != null) {
            buttonPower2.setOnClickListener(v -> {
                inputFormula.append("^2");
                displayFormula.append("²");
                updateDisplay();
            });
        }

        Button buttonPowerY = findViewById(R.id.buttonPowerY);
        if (buttonPowerY != null) {
            buttonPowerY.setOnClickListener(v -> {
                inputFormula.append("^");
                displayFormula.append("^");
                updateDisplay();
            });
        }

        Button buttonRootY = findViewById(R.id.buttonRootY);
        if (buttonRootY != null) {
            buttonRootY.setOnClickListener(v -> {
                inputFormula.append("^(1/");
                displayFormula.append("^(1/");
                updateDisplay();
            });
        }

        Button buttonExp = findViewById(R.id.buttonExp);
        if (buttonExp != null) {
            buttonExp.setOnClickListener(v -> {
                inputFormula.append("exp(");
                displayFormula.append("exp(");
                updateDisplay();
            });
        }

        Button buttonFact = findViewById(R.id.buttonFact);
        if (buttonFact != null) {
            buttonFact.setOnClickListener(v -> {
                inputFormula.append("fact(");  // mxParser používa funkciu fact pre faktoriál
                displayFormula.append("!");
                updateDisplay();
            });
        }

        // Ovládacie tlačidlá
        Button buttonClear = findViewById(R.id.buttonClear);
        if (buttonClear != null) {
            buttonClear.setOnClickListener(v -> clear());
        }

        Button buttonEquals = findViewById(R.id.buttonEquals);
        if (buttonEquals != null) {
            buttonEquals.setOnClickListener(v -> calculate());
        }
    }

    private void setupNumberButton(int buttonId, String number) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                if (displayFormula.toString().equals("0") && !number.equals(".")) {
                    displayFormula.setLength(0);
                    inputFormula.setLength(0);
                }
                inputFormula.append(number);
                displayFormula.append(number);
                updateDisplay();
            });
        } else {
            Log.e("MainActivity", "Button with ID " + buttonId + " not found");
        }
    }

    private void setupOperatorButton(int buttonId, String operator) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                inputFormula.append(operator);
                displayFormula.append(operator);
                updateDisplay();
            });
        } else {
            Log.e("MainActivity", "Operator button with ID " + buttonId + " not found");
        }
    }

    private void setupConstantButton(int buttonId, String jsValue, String displayValue) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                inputFormula.append(jsValue);
                displayFormula.append(displayValue);
                updateDisplay();
            });
        } else {
            Log.e("MainActivity", "Constant button with ID " + buttonId + " not found");
        }
    }

    private void setupFunctionButton(int buttonId, String jsFunction, String displayFunction) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                inputFormula.append(jsFunction).append("(");
                displayFormula.append(displayFunction).append("(");
                updateDisplay();
            });
        } else {
            Log.e("MainActivity", "Function button with ID " + buttonId + " not found");
        }
    }

    private void updateDisplay() {
        if (displayFormula.length() == 0) {
            display.setText("0");
        } else {
            display.setText(displayFormula.toString());
        }
    }

    private void clear() {
        inputFormula.setLength(0);
        displayFormula.setLength(0);
        display.setText("0");
    }

    private void calculate() {
        if (inputFormula.length() > 0) {
            try {
                // Preveďte vstupný výraz na formát podporovaný mxParser
                String formula = inputFormula.toString();

                // Nahradiť "^" a "!" pre faktoriál
                formula = formula.replace("**", "^");  // mxParser používa ^ pre mocniny
                formula = formula.replace("!", "fact");  // mxParser používa funkciu fact pre faktoriál

                // Vytvorenie objektu Expression pre mxParser
                Expression exp = new Expression(formula);

                // Vyhodnotenie výrazu
                double result = exp.calculate();

                // Formátovanie výsledku
                String resultStr;
                if (result == Math.floor(result)) {
                    resultStr = String.format("%.0f", result);
                } else {
                    resultStr = String.valueOf(result);
                }

                // Aktualizácia zobrazenia
                clear();
                inputFormula.append(resultStr);
                displayFormula.append(resultStr);
                updateDisplay();

            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Calculation error", e);
            }
        }
    }
}
