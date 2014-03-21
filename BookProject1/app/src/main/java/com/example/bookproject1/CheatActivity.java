package com.example.bookproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by aschey on 1/2/14.
 */

public class CheatActivity extends ActionBarActivity {
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    public static final String KEY_TEXT_VIEW = "text_view";
    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.bookproject1.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.bookproject1.answer_shown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        setAnswerShown(false);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        if (savedInstanceState != null) {
            mAnswerTextView.setText(savedInstanceState.getString(KEY_TEXT_VIEW));
            // if the answer has been shown, the user has cheated
            if (mAnswerTextView.getText().toString() != null) {
                setAnswerShown(true);
            }
        }

        Button mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                }
                else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShown(true);
            }
        });

        Button mBack = (Button)findViewById(R.id.destroyCheat);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use getFragmentManager().popBackStack() for fragments
                finish();
            }
        });
    }


    private void setAnswerShown(boolean answerShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_ANSWER_SHOWN, answerShown);
        // resultIntent becomes data parameter of onActivityResult()
        setResult(RESULT_OK, resultIntent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_VIEW, mAnswerTextView.getText().toString());
    }
}
