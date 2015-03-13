package com.app.adex;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.app.utils.GlobalFunc;

import java.util.Calendar;
import java.util.List;

public abstract class SuperActivity extends BaseActivity
{
	private final int BACK_PRESS_MAX_INTERVAL = 2000;
	private boolean isPressedBack = false;
	private long backPressedTime = 0;

	public static SuperActivity mInstance = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new MyUnhandledExceptionHandler());

		mInstance = this;
	}

	@Override
	public void onResume()
	{
		super.onResume();

		int nDir = getIntent().getIntExtra(GlobalFunc.ANIM_DIRECTION(), -1);
		if (nDir == GlobalFunc.ANIM_COVER_FROM_LEFT())
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		else if (nDir == GlobalFunc.ANIM_COVER_FROM_RIGHT())
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
		else
			overridePendingTransition(0, 0);
	}

	@Override
	public void initialize()
	{
		super.initialize();
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		ViewGroup rootView = (ViewGroup)getWindow().getDecorView().findViewById(getMainLayoutRes());
		hideKeyboardsInView(rootView);
	}

	private void hideKeyboardsInView(ViewGroup view)
	{
		if (view == null)
			return;

		int nCount = view.getChildCount();
		for (int i = 0; i < nCount; i++)
		{
			View childView = view.getChildAt(i);
			if (childView instanceof ViewGroup)
			{
				hideKeyboardsInView((ViewGroup)childView);
			}
			else if (childView instanceof EditText)
			{
				GlobalFunc.hideKeyboardFromText((EditText)childView, SuperActivity.this);
			}
		}
	}

	protected void onNewIntent(Intent intent)
	{
		super.setIntent(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (isLastActivity())
			{
				try
				{
					if (!isPressedBack)
					{
						GlobalFunc.showTextToast(SuperActivity.this, getResources().getString(R.string.exit_app));
						backPressedTime = Calendar.getInstance().getTimeInMillis();
						isPressedBack = true;
					}
					else
					{
						if (Calendar.getInstance().getTimeInMillis() - backPressedTime < BACK_PRESS_MAX_INTERVAL)
						{
							SuperActivity.this.finish();
							System.exit(1);
						}
						else
						{
							GlobalFunc.showTextToast(SuperActivity.this, getResources().getString(R.string.exit_app));
							backPressedTime = Calendar.getInstance().getTimeInMillis();
						}
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				finishWithAnimation();
			}

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}


	public boolean isLastActivity()
	{
		ActivityManager mngr = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

		if (taskList.get(0).numActivities != 1 || !taskList.get(0).topActivity.getClassName().equals(this.getClass().getName()))
			return false;

		return true;
	}

	public void finishWithAnimation()
	{
		SuperActivity.this.finish();
	}

	public Point mScrSize = new Point(0, 0);
	public Point getScreenSize()
	{
		Point ptTemp = GlobalFunc.getScreenSize(getApplicationContext());
		ptTemp.y -= GlobalFunc.statusBarHeight(this);

		return ptTemp;
	}

	private class MyUnhandledExceptionHandler implements Thread.UncaughtExceptionHandler
	{
		private Thread.UncaughtExceptionHandler defaultUEH;

		public MyUnhandledExceptionHandler() {
			this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		}

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			ex.printStackTrace();
			defaultUEH.uncaughtException(thread, ex);
		}

	}
}
