package com.jilcreation.adex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.jilcreation.utils.Utils;
import com.metaio.cloud.plugin.MetaioCloudPlugin;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IMetaioSDKAndroid;
import com.metaio.tools.SystemInfo;

public class SplashActivity extends Activity
{

	/**
	 * Progress dialog
	 */
	private ProgressDialog progressDialog;

    /**
     * Load native libs required by the Metaio SDK
     */
    protected void loadNativeLibs() throws UnsatisfiedLinkError, RuntimeException
    {
        IMetaioSDKAndroid.loadNativeLibs();
        MetaioDebug.log(Log.INFO, "MetaioSDK libs loaded for " + SystemInfo.getDeviceABI() + " using "
				+ com.metaio.sdk.jni.SystemInfo.getAvailableCPUCores() + " CPU cores");
    }

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_scansplash);

        try {
            loadNativeLibs();
        } catch (Exception e) {
            Utils.showErrorForCloudPluginResult(MetaioCloudPlugin.ERROR_CPU_NOT_SUPPORTED, this);
            return;
        }

		// Initialize cloud plugin in an AsyncTask
		CloudPluginInitializerTask task = new CloudPluginInitializerTask();
		task.execute(1);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}

	/**
	 * Launch Cloud plugin's live view
	 */
	private void launchLiveView()
	{
		// Set your channel id in /res/values/channelid.xml
		final int myChannelId = getResources().getInteger(R.integer.channelid);

		// if you have set a channel ID, then load it directly
		if (myChannelId != -1)
		{
			startChannel(myChannelId, true);
		}
	}

	public void startChannel(int channelId, boolean andFinishActivity)
	{
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		intent.putExtra(getPackageName() + ".CHANNELID", channelId);
		startActivity(intent);

		if (andFinishActivity)
			finish();
	}

	private class CloudPluginInitializerTask extends AsyncTask<Integer, Integer, Integer>
	{

		@Override
		protected void onPreExecute()
		{
		       String myAppName = getResources().getString(R.string.app_name);
			progressDialog = ProgressDialog.show(SplashActivity.this, myAppName, "Starting up...");
		}

		@Override
		protected Integer doInBackground(Integer... params)
		{

			// TODO Set authentication if a private channel is used
			// MetaioCloudPlugin.setAuthentication("username", "password");

			// This will initialize everything the plugin needs
			final int result = MetaioCloudPlugin.initialize(this, getApplicationContext());

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... progress)
		{

		}

		@Override
		protected void onPostExecute(Integer result)
		{
			if (progressDialog != null)
			{
				progressDialog.cancel();
				progressDialog = null;
			}

			if (result == MetaioCloudPlugin.SUCCESS)
				launchLiveView();
			else
				Utils.showErrorForCloudPluginResult(result, SplashActivity.this);
		}

	}
}
