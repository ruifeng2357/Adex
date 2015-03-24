package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.utils.GlobalFunc;
import com.crashlytics.android.Crashlytics;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogoActivity extends SuperActivity {
    AppPreferences preferences;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_logo);

        preferences = new AppPreferences(this);

        Handler handler= new Handler() {
            public void handleMessage(Message msg){
                nextActivity();
            }
        };

        if (preferences.getUserId() != 0)
            handler.sendEmptyMessageDelayed(0, 2000);
        else {
            ServerManager.addUser(handlerAddUser);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void nextActivity() {
        Intent intent = null;
        if (preferences.getFirstRun()) {
            intent = new Intent(LogoActivity.this, SettingActivity.class);
        }
        else {
            intent = new Intent(LogoActivity.this, MainMenuActivity.class);
        }
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        LogoActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        startActivity(intent);
        LogoActivity.this.finish();
    }

    private AsyncHttpResponseHandler handlerAddUser = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.

            try {
                JSONArray jsonArray = new JSONArray(content);
                if (jsonArray == null || jsonArray.length() < 1) {
                    return;
                } else {
                    JSONObject object = jsonArray.getJSONObject(0);

                    long userId = object.getLong("user_id");
                    preferences.setUserId(userId);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            nextActivity();
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
            nextActivity();
        }
    };
}
