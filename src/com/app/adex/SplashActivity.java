package com.app.adex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import com.app.utils.GlobalFunc;
import com.crashlytics.android.Crashlytics;

public class SplashActivity extends SuperActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_splash);

        Handler handler= new Handler() {
            public void handleMessage(Message msg){
                AppPreferences appPreferences = new AppPreferences(SplashActivity.this);

                Intent intent = null;
                if (appPreferences.getFirstRun()) {
                    intent = new Intent(SplashActivity.this, SettingActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                }
                intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                SplashActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 2000);
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
}
