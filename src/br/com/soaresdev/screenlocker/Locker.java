package br.com.soaresdev.screenlocker;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Locker extends Activity {

	private boolean run = false;
	String tmp_pass = "1234";

	public void moveTaskBack() {
		Log.w("BR_LOCKER", "moving task to back");
		if (this.moveTaskToBack(true) == false) {
			Log.w("BR_LOCKER", "movetaskback == false");
		} else {
			Log.w("BR_LOCKER", "movetaskback == true");
		}
	}

	private void turnFieldsInvisible() {
		run = false;
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		final EditText et2 = (EditText) findViewById(R.id.editText2);
		final EditText et3 = (EditText) findViewById(R.id.editText3);
		final EditText et4 = (EditText) findViewById(R.id.editText4);

		et1.setVisibility(View.INVISIBLE);
		et2.setVisibility(View.INVISIBLE);
		et3.setVisibility(View.INVISIBLE);
		et4.setVisibility(View.INVISIBLE);

	}

	private void clearFields() {
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		final EditText et2 = (EditText) findViewById(R.id.editText2);
		final EditText et3 = (EditText) findViewById(R.id.editText3);
		final EditText et4 = (EditText) findViewById(R.id.editText4);

		et1.setText("");
		et1.requestFocus();

		et4.setText("");
		et3.setText("");
		et2.setText("");
	}

	private void setEditTextControl() {
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		final EditText et2 = (EditText) findViewById(R.id.editText2);
		final EditText et3 = (EditText) findViewById(R.id.editText3);
		final EditText et4 = (EditText) findViewById(R.id.editText4);

		et1.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (et1.length() == 1) {
					Log.w("BR_LOCKER", "ET1 == 1");
					et2.requestFocus();
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		et2.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (et2.length() == 1) {
					Log.w("BR_LOCKER", "ET1 == 1");
					et3.requestFocus();
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		et3.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (et3.length() == 1) {
					Log.w("BR_LOCKER", "ET1 == 1");
					et4.requestFocus();
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		et4.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (et4.length() == 1) {
					String pass = et1.getText().toString()
							+ et2.getText().toString()
							+ et3.getText().toString()
							+ et4.getText().toString();
					Log.w("BR_LOCKER", "PASS: [" + pass + "]");
					if (pass.contains(tmp_pass)) {
						Log.w("BR_LOCKER", "PASS OK");
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,
								0);
						Log.w("BR_LOCKER", "finishing");
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						setResult(50, intent);
						finish();
						// moveTaskBack();
					} else {
						Log.w("BR_LOCKER", "PASS NOT OK");
						clearFields();
					}

				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
	}

	private void RunAnimations() {
		if (run == true)
			return;
		run = true;

		Animation a = AnimationUtils.loadAnimation(this, R.anim.translate);
		a.reset();
		EditText et = (EditText) findViewById(R.id.editText1);
		et.clearAnimation();
		et.startAnimation(a);
		et.setVisibility(View.VISIBLE);

		et = (EditText) findViewById(R.id.editText2);
		et.clearAnimation();
		et.startAnimation(a);
		et.setVisibility(View.VISIBLE);

		et = (EditText) findViewById(R.id.editText3);
		et.clearAnimation();
		et.startAnimation(a);
		et.setVisibility(View.VISIBLE);

		et = (EditText) findViewById(R.id.editText4);
		et.clearAnimation();
		et.startAnimation(a);
		et.setVisibility(View.VISIBLE);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			RunAnimations();
		}
		return true;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			Log.w("BR_LOCKER", "Key code: " + event.getKeyCode());
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_BACK:
				turnFieldsInvisible();
				return true;
			case KeyEvent.KEYCODE_HOME:
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("BR_LOCKER", "Iniciando!");
		KeyguardManager mKeyGuardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		KeyguardLock mLock = mKeyGuardManager.newKeyguardLock("activity_classname");
		mLock.disableKeyguard();

		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.locker);

		setEditTextControl();
	}
}
