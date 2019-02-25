package com.example.williamching.androidprojectn1;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import Model.Question;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    int buttonNumbers[] = {R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9};

    Button buttonDisplay, buttonGenerate, buttonSave, buttonQuit, buttonClear, buttonNegative, buttonDot;
    TextView textViewQuestion, textViewTimer;
    EditText editTextAnswer;
    char possibleOperations[] = {'+','-','*'};
    Question question;
    ArrayList<Question> listQuestions;
    ArrayList<Button> listNumberedButton = new ArrayList<>();
    String difficulty;
    long timeLeft;
    CountDownTimer countDownTimer;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        textViewQuestion = findViewById(R.id.textView_Question);
        textViewQuestion.setText("");

        textViewTimer = findViewById(R.id.textView_Timer);
        textViewTimer.setText("20");

        editTextAnswer = findViewById(R.id.editText_UserAnswer);
        editTextAnswer.setText("");
        editTextAnswer.setShowSoftInputOnFocus(false);


        buttonDisplay = findViewById(R.id.button_Display);
        buttonDisplay.setOnClickListener(this);

        buttonGenerate = findViewById(R.id.button_Generate);
        buttonGenerate.setOnClickListener(this);

        buttonSave = findViewById(R.id.button_Save);
        buttonSave.setOnClickListener(this);

        buttonQuit = findViewById(R.id.button_Quit);
        buttonQuit.setOnClickListener(this);

        buttonClear = findViewById(R.id.button_Clear);
        buttonClear.setOnClickListener(this);

        buttonNegative = findViewById(R.id.button_Negative);
        buttonNegative.setOnClickListener(this);

        buttonDot = findViewById(R.id.button_Dot);
        buttonDot.setOnClickListener(this);

        for (int i = 0; i < buttonNumbers.length; i++){
            Button button = findViewById(buttonNumbers[i]);
            button.setOnClickListener(this);
            listNumberedButton.add(button);
        }

        spinner = findViewById(R.id.spinner_Dificulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulties,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        listQuestions = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;

        if(listNumberedButton.contains(clickedButton)){
            String num = editTextAnswer.getText().toString() + clickedButton.getText().toString();
            editTextAnswer.setText(num);
        }

        switch (v.getId()){

            case R.id.button_Negative:
                if(!editTextAnswer.getText().toString().equals("")){
                    String str = "-"+editTextAnswer.getText().toString();
                    editTextAnswer.setText(str);
                }else{
                    editTextAnswer.setText("-");
                }
                break;

            case R.id.button_Dot:
                if(editTextAnswer.getText().toString().equals("")){
                    editTextAnswer.setText("0.");
                }else{
                    editTextAnswer.append(".");
                }
                break;

            case R.id.button_Quit:
                finish();
                break;

            case R.id.button_Clear:
                editTextAnswer.setText("");
                break;

            case R.id.button_Generate:
                resetTimer();
                textViewTimer.setTextColor(Color.WHITE);
                question = generateQuestion();
                editTextAnswer.setText("");
                textViewQuestion.setText(question.displayQuestion());
                buttonFunctionality(true);
                startTimer();
                break;

            case R.id.button_Display:
                resetTimer();
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("result",listQuestions);
                startActivity(intent);
                break;

            case R.id.button_Save:

                try {
                    if(timeLeft==0){
                        Question.UserAnswer answer = question.new UserAnswer(0);
                    } else {
                        Question.UserAnswer answer = question.new UserAnswer(Integer.valueOf(editTextAnswer.getText().toString()));
                        question.setAnswer(answer);
                        resetTimer();
                    }
                    listQuestions.add(question);
                } catch (NumberFormatException e) {
                    Toast.makeText(this,"Please insert a answer first",Toast.LENGTH_LONG).show();
                }


                break;
        }

    }

    private void resetTimer() {

        if(countDownTimer!=null) {
            countDownTimer.cancel();
        }

        setTimer(difficulty);
        buttonFunctionality(false);
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeft, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeLeft = millisUntilFinished;
                updateCounter();

            }

            @Override
            public void onFinish() {

                buttonFunctionality(false);
                setTimer(difficulty);
            }
        }.start();
    }

    public void updateCounter(){

        Toast toast = Toast.makeText(this, "Time is up, no answer allow", Toast.LENGTH_LONG);
        int seconds = (int) (timeLeft / 1000);
        String timeLeft = String.format(Locale.getDefault(), "%02d", seconds);
        if(seconds <= getResources().getInteger(R.integer.criticalTime)) textViewTimer.setTextColor(Color.RED);
        if(seconds == 0) {

            editTextAnswer.setText("Time is up!");
            toast.show();
        }
        textViewTimer.setText(timeLeft);
    }

    private Question generateQuestion() {

        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        int number = random.nextInt(3);
        char operation = possibleOperations[number];
        int result = 0;

        switch (operation){
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case '*':
                result = number1 * number2;
                break;
        }

        Question newQuestion = new Question(number1,number2,operation,result);
        setTimer(difficulty);

        return newQuestion;

    }

    private void buttonFunctionality (boolean bool){

        for (int i = 0; i < listNumberedButton.size(); i++){
            listNumberedButton.get(i).setEnabled(bool);
        }
        spinner.setEnabled(!bool);
        buttonSave.setEnabled(bool);
        if(bool) {
            buttonSave.setBackgroundResource(R.drawable.optionbackground);

        } else {
            buttonSave.setBackgroundResource(R.color.orangeDark);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        difficulty = parent.getItemAtPosition(position).toString();
        setTimer(difficulty);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setTimer(String difficulty){

        textViewTimer.setTextColor(Color.WHITE);

        switch (difficulty){
            case "Easy":
                timeLeft = getResources().getInteger(R.integer.easy);
                textViewTimer.setText(String.valueOf(timeLeft/1000));
                break;

            case "Normal":
                timeLeft = getResources().getInteger(R.integer.normal);
                textViewTimer.setText(String.valueOf(timeLeft/1000));
                break;

            case "Hard":
                timeLeft = getResources().getInteger(R.integer.hard);
                textViewTimer.setText(String.valueOf(timeLeft/1000));
                break;
        }

    }
}
