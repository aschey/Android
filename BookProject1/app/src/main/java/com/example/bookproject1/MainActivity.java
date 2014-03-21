/*
GeoQuiz
 */

package com.example.bookproject1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;

public class MainActivity extends ActionBarActivity {

    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };
    private boolean[] questionsCheated = {false, false, false, false, false};
    private int mCurrentIndex = 0;
    private static final int DEFAULT_INDEX = 0;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT = "cheat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);

        // get user interface
        // inflate layout, instantiate all widgets, takes layout id as parameter
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("oceans");
        }

        if (savedInstanceState != null) {
            // 0 = default value
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, DEFAULT_INDEX);
            questionsCheated = savedInstanceState.getBooleanArray(KEY_CHEAT);
        }

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        TextView mApiLevelTextView = (TextView)findViewById(R.id.api_level);
        mApiLevelTextView.setText("API level " + Build.VERSION.SDK_INT);

        LinearLayout mLayout = (LinearLayout)findViewById(R.id.layout);
        mLayout.setOnClickListener(setListener());

        Button mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        Button mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        Button mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cheatIntent = new Intent(MainActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                cheatIntent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                // used instead of startActivity() in order to hear back from child class
                // result comes back through onActivityResult()
                startActivityForResult(cheatIntent, 0);
            }
        });

        ImageButton mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(setListener());

        ImageButton mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = 5;
                }
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                //mIsCheater = false;
                updateQuestion();
            }
        });
        updateQuestion();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_CHEAT, questionsCheated);
    }


    private View.OnClickListener setListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        };
    }


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId;

        if (questionsCheated[mCurrentIndex]) {
            messageResId = R.string.judgment_toast;
        }
        else if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
        }
        else {
                messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        questionsCheated[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
