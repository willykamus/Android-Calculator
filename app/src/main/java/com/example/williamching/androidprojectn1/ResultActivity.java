package com.example.williamching.androidprojectn1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Question;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView_Questions, textView_FinalResult;
    ImageButton buttonBack;
    ImageView imageView_Face;
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
        buttonBack = findViewById(R.id.button_Back);
        buttonBack.setOnClickListener(this);
        list = (ArrayList<Question>) getIntent().getExtras().getSerializable("result");


        for (int i = 0; i < list.size(); i++){
            textView_Questions.append(list.get(i).toString());
            if(list.get(i).checkAnswer()){
                correctCounter++;
            }
        }

        String str = Math.round(correctCounter/list.size()*100) + "% correct answers\n" +
                Math.round((list.size()-correctCounter)/list.size()*100) + "% wrong answers";

        textView_FinalResult.setText(str);

        imageView_Face = findViewById(R.id.imageView_Face);

        setFace();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Back:
                finish();
                break;
        }
    }

    public void setFace(){

        if(Math.round(correctCounter/list.size()*100)>=50){
            imageView_Face.setImageResource(R.drawable.nerd_face);
        } else {
            imageView_Face.setImageResource(R.drawable.sad_face);
        }

    }
}
