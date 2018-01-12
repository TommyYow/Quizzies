package com.example.user.quizzies;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.quizzies.Model.Transaction;

/**
 * Created by User on 13/1/2018.
 */

public class HistoryHolder extends RecyclerView.ViewHolder {
    private final TextView mDateTimeField;
    private final TextView mQuesField;
    private final TextView mScoreField;
    private final RelativeLayout mHistoryContainer;
    private final LinearLayout mHistory;

    public HistoryHolder(View itemView){
        super(itemView);

        mDateTimeField = itemView.findViewById(R.id.datetime_text);
        mQuesField = itemView.findViewById(R.id.ques_text);
        mScoreField = itemView.findViewById(R.id.score_text);
        mHistoryContainer = itemView.findViewById(R.id.history_container);
        mHistory = itemView.findViewById(R.id.history);
    }

    public void bind(Transaction transaction) {
        setDateTime(transaction.getDateTime());
        setQues(transaction.getQues());
        setScore(transaction.getScore());
    }

    private void setDateTime(String dateTime) {
        mDateTimeField.setText(dateTime);
    }

    private void setQues(String ques) {
        mQuesField.setText("Question: " + ques);
    }

    private void setScore(String score) {
        if(score.equalsIgnoreCase("+ 1")) {
            mScoreField.setTextColor(Color.GREEN);
        }else if(score.equalsIgnoreCase("- 1")){
            mScoreField.setTextColor(Color.RED);
        }
        mScoreField.setText(score);
    }
}
