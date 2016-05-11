package com.example.asus.puzzleprojectdemo;

/**
 * Created by Asus on 5/9/2016.
 */
public class QuestionPuzzle {
    private int image;
    private String answer;
    private String question;
    private Vector2 positionFirst;
    private boolean isCross;

    public QuestionPuzzle(){

    }

    public QuestionPuzzle(int image, String answer, String question, Vector2 positionFirst, boolean isCross) {
        this.image = image;
        this.answer = answer;
        this.question = question;
        this.positionFirst = positionFirst;
        this.isCross = isCross;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Vector2 getPositionFirst() {
        return positionFirst;
    }

    public void setPositionFirst(Vector2 positionFirst) {
        this.positionFirst = positionFirst;
    }

    public boolean isCross() {
        return isCross;
    }

    public void setIsCross(boolean isCross) {
        this.isCross = isCross;
    }
}
