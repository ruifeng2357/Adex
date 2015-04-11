package com.jilcreation.adex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jilcreation.model.STExhibitorInfo;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExhibitorActivity extends SuperActivity implements View.OnClickListener {
    ImageView imageBack;
    LinearLayout llContent;

    ArrayList<STExhibitorInfo> arrExhibitorInfos = new ArrayList<STExhibitorInfo>();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitors);

        startProgress();
        ServerManager.getAllHibitors(handlerAllExhibitors);
    }

    @Override
    public void initialize() {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);

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
        if (v == imageBack) {
            finish();
        }
    }

    private AsyncHttpResponseHandler handlerAllExhibitors = new AsyncHttpResponseHandler()
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

                        STExhibitorInfo exhibitorInfo = new STExhibitorInfo();
                        exhibitorInfo = STExhibitorInfo.decodeFromJSON(object);

                        arrExhibitorInfos.add(exhibitorInfo);
                    }

                    showDeals();
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

    private void showDeals() {
        llContent.removeAllViews();

        int nCount = arrExhibitorInfos.size();

        for (int i = 0; i < nCount ; i++) {
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_exhibitoritem, null);
            ResolutionSet._instance.iterateChild(view);

            STExhibitorInfo exhibitorInfo = arrExhibitorInfos.get(i);

            TextView textView = (TextView) view.findViewById(R.id.textName);
            textView.setText(exhibitorInfo.merchantName);
            TextView textBooth = (TextView) view.findViewById(R.id.textBooth);
            textBooth.setText(exhibitorInfo.booth);

            llContent.addView(view);
        }
    }
}
