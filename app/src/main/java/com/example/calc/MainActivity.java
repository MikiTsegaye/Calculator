package com.example.calc;

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


    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton buttonDevide,buttonPlus,buttonMinus,buttonMultiply,buttonEqual;
    MaterialButton button1, button2,button3,button4,button5,button6,button7,button8,button9,button0;
    MaterialButton buttonAC,buttonDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultTv=findViewById(R.id.digits);
        solutionTv=findViewById(R.id.solution);

        assignId(buttonC,R.id.button_c);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonDevide,R.id.button_devide);
        assignId(buttonAC,R.id.button_AC);
        assignId(buttonBrackClose,R.id.button_close_bracket);
        assignId(buttonBrackOpen,R.id.button_open_bracket);
        assignId(buttonEqual,R.id.button_equal);
        assignId(buttonDot,R.id.button_dot);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void assignId(MaterialButton btn, int id){
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String btnTxt = btn.getText().toString();
        String dataToCalc = solutionTv.getText().toString();

        if(btnTxt.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(btnTxt.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(btnTxt.equals("C")){
            dataToCalc=dataToCalc.substring(0,dataToCalc.length()-1);
        }else{
            if (btnTxt.equals("x")){ btnTxt = "*";}
            dataToCalc = dataToCalc+btnTxt;
        }
        solutionTv.setText(dataToCalc);

        String finalResult= getResult(dataToCalc);
        if(!finalResult.equals("Error")){
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data){
        try {
            double result = Calculator.evaluate(data);
            String finalResult = String.valueOf(result);
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}