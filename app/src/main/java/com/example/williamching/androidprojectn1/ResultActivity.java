package com.example.williamching.androidprojectn1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Question;

public class ResultActivity extends AppCompatActivity {

    TextView textView_Questions, textView_FinalResult;
    ArrayList<Question> list;
    float correctCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
    }

    private void initialize() {

        textView_FinalResult = findViewById(R.id.textView_FinalResult);
        textView_Questions = findViewById(R.id.textView_Questions);
        list = (ArrayList<Question>) getIntent().getExtras().getSerializable("result");


        for (int i = 0; i < list.size(); i++){
            textView_Questions.append(list.get(i).toString());
            if(list.get(i).checkAnswer()){
                correctCounter++;
            }
        }

        String str = Math.round(correctCounter/list.size()*100) + " % correct answers\n" +
                Math.round((list.size()-correctCounter)/list.size()*100) + "% wrong answers";

        textView_FinalResult.setText(str);

    }
}
