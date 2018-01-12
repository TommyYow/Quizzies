package com.example.user.quizzies.Model;

/**
 * Created by User on 13/1/2018.
 */

public class Transaction {
    private String dateTime;
    private String ques;
    private String score;
    private String uid;

    public Transaction() {
    }

    public Transaction(String dateTime, String ques, String score, String uid) {
        this.dateTime = dateTime;
        this.ques = ques;
        this.score = score;
        this.uid = uid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
