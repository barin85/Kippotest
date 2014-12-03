package com.example.kippotest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

	public static final String TAG = "QuizActivity";
	public static final String EXTRA_ANSWER_IS_TRUE = 
			"com.example.kippotest.answer_is_true";

	
	Button mTrueButton;
	Button mFalseButton;
	Button mNextButton;
	Button mCheatButton;
	
	private boolean mIsCheater;
	private static final String KEY_INDEX = "index";
	
	TextView mQuestionTextView;
	
	private int mCurrentIndex = 0;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_one, true),
			new TrueFalse(R.string.question_two, false),
			new TrueFalse(R.string.question_tree, true),
			new TrueFalse(R.string.question_four, true),
			new TrueFalse(R.string.question_five, false),
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		mIsCheater = false;
		
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}


		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				checkAnswer(true);

			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				checkAnswer(false);
			}
		});
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_textView);
		updateQuestion();
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
				mIsCheater = false;
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

				startActivityForResult(i, 0);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
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
	
	@Override
	public void onStart() {
		super.onStart();
//		Toast.makeText(QuizActivity.this, "onStart() called",
//				Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStart() called");
		
	}

	@Override
	public void onPause() {
		super.onPause();
//		Toast.makeText(QuizActivity.this, "onPause() called",
//				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
//		Toast.makeText(QuizActivity.this, "onResume() called",
//				Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onResume() called");
	}

	@Override
	public void onStop() {
		super.onStop();
//		Toast.makeText(QuizActivity.this, "onStop() called",
//				Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onStop() called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		
		if (mIsCheater) {
			messageResId = R.string.judgment_toast;

		} else {

			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.toast_correct;
			} else {
				messageResId = R.string.toast_incorrect;
			}
		}
		
		Toast.makeText(QuizActivity.this, messageResId,
				Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);	
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,
				false);
	}


}
