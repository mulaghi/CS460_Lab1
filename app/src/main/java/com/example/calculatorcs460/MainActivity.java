package com.example.calculatorcs460;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result_tv, solution_tv;
    MaterialButton button_c, button_openBracket, button_closedBracket;
    MaterialButton button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    MaterialButton button_divide, button_multiply, button_plus, button_subtract, button_equals;
    MaterialButton button_ac, button_period;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        /**
         * Initializes the TextView variables and assigns IDs to the buttons using the assignID method.
         */
        result_tv = findViewById(R.id.result_tv);
        solution_tv = findViewById(R.id.solution_tv);

        assignID(button_c, R.id.button_c);
        assignID(button_ac, R.id.button_ac);

        assignID(button_openBracket, R.id.button_openBracket);
        assignID(button_closedBracket, R.id.button_closedBracket);
        assignID(button_period, R.id.button_period);
        assignID(button_equals, R.id.button_equals);

        assignID(button_divide, R.id.button_divide);
        assignID(button_multiply, R.id.button_multiply);
        assignID(button_plus, R.id.button_plus);
        assignID(button_subtract, R.id.button_subtract);

        assignID(button_1, R.id.button_1);
        assignID(button_2, R.id.button_2);
        assignID(button_3, R.id.button_3);
        assignID(button_4, R.id.button_4);
        assignID(button_5, R.id.button_5);
        assignID(button_6, R.id.button_6);
        assignID(button_7, R.id.button_7);
        assignID(button_8, R.id.button_8);
        assignID(button_9, R.id.button_9);
        assignID(button_0, R.id.button_0);

    }

    /**
     * Assigns a given ID to a MaterialButton and sets an OnClickListener for it.
     *
     * @param btn the MaterialButton the ID will be assigned to
     * @param id  the ID used to find and assign the button
     */
    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    /**
     * Handles the click event for a MaterialButton.
     * Depending on which button is clicked will result in the proper button action
     *
     * @param view the View that was clicked, which is cast to MaterialButton
     */

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        String dataToCalculate = solution_tv.getText().toString();
        if(buttonText.equals("AC")){
            solution_tv.setText("");
            result_tv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solution_tv.setText(result_tv.getText());
            return;
        }

        if(buttonText.equals("C")){
            if(dataToCalculate.length() == 1){
                solution_tv.setText("");
                result_tv.setText("0");
                return;
            }else{
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
            }

        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }

        solution_tv.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);
        if(!finalResult.equals("Err")){
            result_tv.setText(finalResult);
        }
    }

    /**
     * Gets the result of the expression in the given data string using JavaScript.
     *
     * @param data the mathematical expression to evaluate
     * @return the result of the evaluation as a String, or "Err" if an exception occurs
     */
    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable,data,"Javascript",1, null).toString();


        }catch(Exception e){
            return "Err";
        }
    }
}