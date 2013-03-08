package br.com.soaresdev.screenlocker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	public static int FINISH = 0x200;

	private void setBright(int value) {
		Log.w("BR_DEBUG", "setting bright - val: " + value);	
		android.provider.Settings.System.putInt(getContentResolver(),  
				android.provider.Settings.System.SCREEN_BRIGHTNESS, value);
	}
	
	private void moveTaskBack() {
		Log.w("BR_DEBUG", "moving task to back");
		if(this.moveTaskToBack(true) == false) {
			Log.w("BR_DEBUG", "movetaskback == false");
		} else {
			Log.w("BR_DEBUG", "movetaskback == true");
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			Log.w("BR_DEBUG", "Key code: " + event.getKeyCode());
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_BACK:
				return true;
			case KeyEvent.KEYCODE_HOME:
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	protected void onResume() {
		Log.w("BR_DEBUG", "on resume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.w("BR_DEBUG", "on pause");
		super.onPause();
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    Log.w("BR_DEBUG","requestCode = "+requestCode+" / resultCode = " +resultCode );

	    	if(requestCode == MainActivity.FINISH) {
	    		Log.w("BR_DEBUG", "voltou do locker");
	    		moveTaskBack();
	    	}
	    	
	    super.onActivityResult(requestCode, resultCode, intent);
	}
	
//	@Override
//	public void onAttachedToWindow() {
//	    super.onAttachedToWindow();
//	    this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);           
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("BR_DEBUG", "Iniciando!");

		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		/* Capture screen saver action */
		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				setBright(255);
				Log.w("BR_DEBUG_CATCH", "ACTION SCREEN OFF");
			}
		}, new IntentFilter(Intent.ACTION_SCREEN_OFF));

		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.w("BR_DEBUG_CATCH", "ACTION SCREEN ON");
				setBright(0);
				Intent i = new Intent(getApplicationContext(), Locker.class);
				startActivityForResult(i, MainActivity.FINISH);
			}
		}, new IntentFilter(Intent.ACTION_SCREEN_ON));
		
		this.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.w("BR_DEBUG_CATCH", "ACTION BOOT_COMPLETED");
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
		}, new IntentFilter(Intent.ACTION_BOOT_COMPLETED));
		
		
		Intent i = new Intent(getApplicationContext(), Locker.class);
		startActivityForResult(i, MainActivity.FINISH);
	}

}
