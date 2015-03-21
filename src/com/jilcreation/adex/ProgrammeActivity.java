package com.jilcreation.adex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jilcreation.model.STProgrammeInfo;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProgrammeActivity extends SuperActivity implements View.OnClickListener {
    private final int MODE_MAIN = 1;
    private final int MODE_SHARE = 2;
    private final int MODE_FILM = 3;
    private int type = MODE_MAIN;

    private int curDayNumber = -1;

    protected TextView textMain;
    protected TextView textShare;
    protected TextView textFilm;
    protected ImageView imageBack;
    protected LinearLayout llContent;

    ArrayList<STProgrammeInfo> arrProgrammeInfos = new ArrayList<STProgrammeInfo>();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme);

        callWebService();
    }

    @Override
    public void initialize() {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);
        textMain = (TextView) findViewById(R.id.textMain);
        textMain.setOnClickListener(this);
        textShare = (TextView) findViewById(R.id.textShare);
        textShare.setOnClickListener(this);
        textFilm = (TextView) findViewById(R.id.textFlim);
        textFilm.setOnClickListener(this);
        llContent = (LinearLayout) findViewById(R.id.llContent);
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
        if ( v == textMain ) {
            type = MODE_MAIN;

            textMain.setBackground(getResources().getDrawable(R.drawable.roundleftselframe));
            textMain.setTextColor(getResources().getColor(R.color.adex_white));
            textShare.setBackground(getResources().getDrawable(R.drawable.rectdiselframe));
            textShare.setTextColor(getResources().getColor(R.color.adex_programmemenublue));
            textFilm.setBackground(getResources().getDrawable(R.drawable.roundrightdiselframe));
            textFilm.setTextColor(getResources().getColor(R.color.adex_programmemenublue));

            callWebService();
        }
        else if (v == textShare) {
            type = MODE_SHARE;

            textMain.setBackground(getResources().getDrawable(R.drawable.roundleftdiselframe));
            textMain.setTextColor(getResources().getColor(R.color.adex_programmemenublue));
            textShare.setBackground(getResources().getDrawable(R.drawable.rectselframe));
            textShare.setTextColor(getResources().getColor(R.color.adex_white));
            textFilm.setBackground(getResources().getDrawable(R.drawable.roundrightdiselframe));
            textFilm.setTextColor(getResources().getColor(R.color.adex_programmemenublue));

            callWebService();
        }
        else if ( v == textFilm ) {
            type = MODE_FILM;

            textMain.setBackground(getResources().getDrawable(R.drawable.roundleftdiselframe));
            textMain.setTextColor(getResources().getColor(R.color.adex_programmemenublue));
            textShare.setBackground(getResources().getDrawable(R.drawable.rectdiselframe));
            textShare.setTextColor(getResources().getColor(R.color.adex_programmemenublue));
            textFilm.setBackground(getResources().getDrawable(R.drawable.roundrightselframe));
            textFilm.setTextColor(getResources().getColor(R.color.adex_white));

            callWebService();
        }
        else if (v == imageBack) {
            finish();
        }
    }

    private void callWebService() {
        arrProgrammeInfos.clear();
        startProgress();
        ServerManager.getProgrammes(type, handlerProgrammes);
    }

    private AsyncHttpResponseHandler handlerProgrammes = new AsyncHttpResponseHandler()
    {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);    //To change body of overridden methods use File | Settings | File Templates.
            stopProgress();

            try {
                JSONArray jsonArray = new JSONArray(content);
                if (jsonArray == null) {
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        STProgrammeInfo programmeInfo = new STProgrammeInfo();
                        programmeInfo = STProgrammeInfo.decodeFromJSON(object);

                        arrProgrammeInfos.add(programmeInfo);
                    }

                    showProgrammes();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);    //To change body of overridden methods use File | Settings | File Templates.
            stopProgress();
        }
    };

    private void showProgrammes() {
        llContent.removeAllViews();

        int nCount = arrProgrammeInfos.size();
        for (int i = 0; i < nCount; i++) {
            STProgrammeInfo stProgrammeInfo = arrProgrammeInfos.get(i);

            if (curDayNumber != stProgrammeInfo.dayNumber) {
                curDayNumber = stProgrammeInfo.dayNumber;

                LayoutInflater inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View viewHeader = inflater.inflate(R.layout.view_programitemheader, null);
                ResolutionSet._instance.iterateChild(viewHeader);
                TextView textHeader = (TextView) viewHeader.findViewById(R.id.textHeaderName);
                textHeader.setText(stProgrammeInfo.dayName + " " + stProgrammeInfo.dayNumber + " (" + stProgrammeInfo.dateFormat + ")");

                llContent.addView(viewHeader);
            }

            LayoutInflater inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View viewData = inflater.inflate(R.layout.view_programitemdata, null);
            ResolutionSet._instance.iterateChild(viewData);
            TextView textHeader = (TextView) viewData.findViewById(R.id.textData);
            textHeader.setText(stProgrammeInfo.startTime + " - " + stProgrammeInfo.endTime + "    " + stProgrammeInfo.speackerName + ": " + stProgrammeInfo.desc );

            llContent.addView(viewData);
        }
    }
}
