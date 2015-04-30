package com.xurface.geoquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import models.TrueFalse;

public class QuizActivity extends ActionBarActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    private final static String TAG = "QuizActivity";
    private final static String KEY_INDEX = "Index";
    private final static String KEY_CHEATER = "ISCHEATER";

    private boolean mIsCheater;

    private boolean[] mIsCheaterBank = new boolean[]{
            false, false, false, false, false
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d(TAG, "onCreate() called");

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            //mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            mIsCheaterBank = savedInstanceState.getBooleanArray(KEY_CHEATER);
        }

        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });


        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                //mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mQuestionBank.length + mCurrentIndex - 1) % mQuestionBank.length;
                //mIsCheater = false;
                updateQuestion();
            }
        });


        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmQuestion();

        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
        int messageResourceId = 0;
        if(mIsCheaterBank[mCurrentIndex]){
         messageResourceId = R.string.judment_toast;
        }else{
            if(answerTrue == userPressedTrue){
                messageResourceId = R.string.toast_correct;
            }else{
                messageResourceId = R.string.toast_incorrect;
            }
        }

        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        Log.d(TAG, "onSaveInstanceState() called");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBooleanArray(KEY_CHEATER, mIsCheaterBank);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }
        //mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        mIsCheaterBank[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
