package com.example.kippotest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

	Button mTrueButton;
	Button mFalseButton;
	Button mNextButton;
	
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

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;

		if (userPressedTrue == answerIsTrue) {
			messageResId = R.string.toast_correct;
		} else {
			messageResId = R.string.toast_incorrect;
		}
		
		Toast.makeText(QuizActivity.this, messageResId,
				Toast.LENGTH_SHORT).show();
	}
}
