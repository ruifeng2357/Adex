package com.jilcreation.adex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.jilcreation.utils.GlobalFunc;
import com.jilcreation.utils.Utils;
import com.metaio.cloud.plugin.MetaioCloudPlugin;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IMetaioSDKAndroid;
import com.metaio.tools.SystemInfo;

public class AdexScanActivity extends SuperActivity implements View.OnClickListener {
    protected Button buttonScan;

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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adexscan);
    }

    @Override
    public void initialize() {
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);
    }

    @Override
    public int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if (progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonScan) {
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
        Intent intent = new Intent(AdexScanActivity.this, MainActivity.class);
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
            progressDialog = ProgressDialog.show(AdexScanActivity.this, myAppName, "Starting up...");
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
                Utils.showErrorForCloudPluginResult(result, AdexScanActivity.this);
        }

    }
}
