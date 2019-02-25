package Model;

import java.io.Serializable;

public class Question implements Serializable{

    private int number1, number2, result;
    private char operation;
    private UserAnswer answer;

    public class UserAnswer implements Serializable {

        private int userAnswer;

        public UserAnswer(int answer){

            this.userAnswer = answer;

        }

        public int getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(int userAnswer) {
            this.userAnswer = userAnswer;
        }
    }

    public Question(int number1, int number2, char operation, int result){

        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
        this.result = result;

    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public UserAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(UserAnswer answer) {
        this.answer = answer;
    }

    public String displayQuestion(){

        return number1 + " " + operation + " " + number2 + " = ";

    }

    @Override
    public String toString() {

        String str;

        if(result == answer.getUserAnswer()){
            str = number1 + " " + operation + " " + number2 + " = " + result + "\nYour answer is correct!\n--------------------------------------------\n";
        } else {
            str = number1 + " " + operation + " " + number2 + " = " + answer.getUserAnswer() + "\nYour answer is wrong!\nThe correct answer is "+ result +"\n--------------------------------------------\n";
        }

        return str;
    }

    public boolean checkAnswer (){
        return result == answer.getUserAnswer();
    }
}
