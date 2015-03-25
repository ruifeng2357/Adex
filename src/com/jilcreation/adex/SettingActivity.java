package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.utils.GlobalFunc;
import org.json.JSONArray;
import org.json.JSONObject;

public class SettingActivity extends SuperActivity implements View.OnClickListener {
    private final int MENUITEM_COUNT = 4;
    RelativeLayout []rlItem = null;

    int curSettingVal = 0;
    AppPreferences appPreferences;

    private Button buttonSubmit;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        appPreferences = new AppPreferences(this);
        curSettingVal = appPreferences.getSettingVal();
    }

    @Override
    public void initialize() {
        rlItem = new RelativeLayout[MENUITEM_COUNT];
        rlItem[0] = (RelativeLayout) findViewById(R.id.rlCourses);
        rlItem[0].setOnClickListener(this);
        rlItem[1] = (RelativeLayout) findViewById(R.id.rlTrips);
        rlItem[1].setOnClickListener(this);
        rlItem[2] = (RelativeLayout) findViewById(R.id.rlGear);
        rlItem[2].setOnClickListener(this);
        rlItem[3] = (RelativeLayout) findViewById(R.id.rlEquip);
        rlItem[3].setOnClickListener(this);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        onDrawMenuItem();
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
    public void onClick(View v) {
        int nVal = 0;
        if (v == rlItem[0]) {
            nVal = (curSettingVal >> 0) & 1;
            if (nVal == 1) {
                curSettingVal = curSettingVal & 0xFFFE;
            }
            else {
                curSettingVal = curSettingVal | 0x1;
            }
        }
        else if (v == rlItem[1]) {
            nVal = (curSettingVal >> 1) & 1;
            if (nVal == 1) {
                curSettingVal = curSettingVal & 0xFFFD;
            }
            else {
                curSettingVal = curSettingVal | 0x2;
            }
        }
        else if (v == rlItem[2]) {
            nVal = (curSettingVal >> 2) & 1;
            if (nVal == 1) {
                curSettingVal = curSettingVal & 0xFFFB;
            }
            else {
                curSettingVal = curSettingVal | 0x4;
            }
        }
        else if (v == rlItem[3]) {
            nVal = (curSettingVal >> 3) & 1;
            if (nVal == 1) {
                curSettingVal = curSettingVal & 0xFFF7;
            }
            else {
                curSettingVal = curSettingVal | 0x8;
            }
        }
        else if (v == buttonSubmit) {
            appPreferences.setFirstRun(false);
            appPreferences.setSettingVal(curSettingVal);

            int a, b, c, d;
            a = (curSettingVal >> 0) & 1;
            b = (curSettingVal >> 1) & 1;
            c = (curSettingVal >> 2) & 1;
            d = (curSettingVal >> 3) & 1;

            startProgress();
            ServerManager.updatePref(handlerUpdatePref, appPreferences.getUserId(), a, b, c, d);

//            Intent intent = new Intent(SettingActivity.this, MainMenuActivity.class);
//            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
//            SettingActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
//            startActivity(intent);
//            finish();

            return;
        }

        onDrawMenuItem();
    }

    private void onDrawMenuItem() {
        int nVal = 0;
        nVal = (curSettingVal >> 0) & 1;
        if (nVal == 1) {
            rlItem[0].setBackground(getResources().getDrawable(R.drawable.recttrans));
        }
        else {
            rlItem[0].setBackground(null);
        }
        nVal = (curSettingVal >> 1) & 1;
        if (nVal == 1) {
            rlItem[1].setBackground(getResources().getDrawable(R.drawable.recttrans));
        }
        else {
            rlItem[1].setBackground(null);
        }
        nVal = (curSettingVal >> 2) & 1;
        if (nVal == 1) {
            rlItem[2].setBackground(getResources().getDrawable(R.drawable.recttrans));
        }
        else {
            rlItem[2].setBackground(null);
        }
        nVal = (curSettingVal >> 3) & 1;
        if (nVal == 1) {
            rlItem[3].setBackground(getResources().getDrawable(R.drawable.recttrans));
        }
        else {
            rlItem[3].setBackground(null);
        }
    }

    private AsyncHttpResponseHandler handlerUpdatePref = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.
            stopProgress();

            try {
                Intent intent = new Intent(SettingActivity.this, MainMenuActivity.class);
                intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                SettingActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
                startActivity(intent);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
            stopProgress();
            GlobalFunc.showTextToast(SettingActivity.this, content);
        }
    };
}
