package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jilcreation.model.STDealInfo;
import com.jilcreation.model.modelmanage.SQLiteDBHelper;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.ui.SmartImageView.SmartImageView;
import com.jilcreation.utils.GlobalFunc;
import com.jilcreation.utils.ResolutionSet;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class AllDealsActivity extends SuperActivity implements View.OnClickListener {
    protected RelativeLayout rlBack;
    protected ImageView imageMenu;
    protected LinearLayout llContent;

    ArrayList<STDealInfo> arrDealInfos = new ArrayList<STDealInfo>();

    SQLiteDBHelper m_db = null;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alldeal);

        m_db = new SQLiteDBHelper(AllDealsActivity.this);
    }

    @Override
    public void initialize() {
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnClickListener(this);

        llContent = (LinearLayout) findViewById(R.id.llContent);

        imageMenu = (ImageView) findViewById(R.id.imageMenu);
        imageMenu.setOnClickListener(this);
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
    public void onResume() {
        super.onResume();

        startProgress();
        ServerManager.getAllDeals(handlerAllDeals);
    }

    @Override
    public void onClick(View v) {
        if ( v == rlBack ) {
            finish();
        }
        else if ( v== imageMenu ) {
        }
    }

    private AsyncHttpResponseHandler handlerAllDeals = new AsyncHttpResponseHandler()
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

                        STDealInfo dealInfo = new STDealInfo();
                        dealInfo = STDealInfo.decodeFromJSON(object);

                        arrDealInfos.add(dealInfo);
                        m_db.insert(dealInfo.dealId, dealInfo.merchantId, dealInfo.productBrand, dealInfo.productName, dealInfo.imageUrl, 0, 0);
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

        int nCount = arrDealInfos.size();

        for (int i = 0; i < (nCount + 1) / 2 ; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_dealitem, null);
            ResolutionSet._instance.iterateChild(view);

            STDealInfo stDealInfo1 = new STDealInfo();
            STDealInfo stDealInfo2 = new STDealInfo();
            try {
                stDealInfo1 = arrDealInfos.get(i * 2);
            } catch (Exception ex) {
                stDealInfo1 = null;
            }
            try {
                stDealInfo2 = arrDealInfos.get(i * 2 + 1);
            } catch (Exception ex) {
                stDealInfo2 = null;
            }

            if (stDealInfo1 != null) {
                SmartImageView imageLeft = (SmartImageView)view.findViewById(R.id.imageLeftDeal);
                imageLeft.setImageUrl(stDealInfo1.imageUrl);
                TextView textBrand = (TextView) view.findViewById(R.id.textLeftBrand);
                textBrand.setText(stDealInfo1.productBrand);
                TextView textName = (TextView) view.findViewById(R.id.textLeftName);
                textName.setText(stDealInfo1.productName);
                ImageView imageFavorited = (ImageView) view.findViewById(R.id.imageLeftFavorite);
                if (m_db.getIsFavorite(stDealInfo1.dealId) == 0) {
                    imageFavorited.setImageResource(R.drawable.icon_heartwhite);
                }
                else {
                    imageFavorited.setImageResource(R.drawable.icon_heartred);
                }
                imageFavorited.setTag(stDealInfo1);
                imageFavorited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
                        if (m_db.getIsFavorite(stDealInfo.dealId) == 0) {
                            m_db.setFavorited(stDealInfo.dealId, 1);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartred);
                        }
                        else {
                            m_db.setFavorited(stDealInfo.dealId, 0);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartwhite);
                        }
                    }
                });

                RelativeLayout rlLeft = (RelativeLayout) view.findViewById(R.id.rlLeftDeal);
                rlLeft.setVisibility(View.VISIBLE);
                rlLeft.setTag(stDealInfo1);
                rlLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
                        m_db.setTapped(stDealInfo.dealId, 1);
                        Intent intent = new Intent(AllDealsActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT", stDealInfo);
                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                        AllDealsActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
                        startActivity(intent);
                    }
                });
            }
            if (stDealInfo2 != null) {
                SmartImageView imageLeft = (SmartImageView)view.findViewById(R.id.imageRightDeal);
                imageLeft.setImageUrl(stDealInfo2.imageUrl);
                TextView textBrand = (TextView) view.findViewById(R.id.textRightBrand);
                textBrand.setText(stDealInfo2.productBrand);
                TextView textName = (TextView) view.findViewById(R.id.textRightName);
                textName.setText(stDealInfo2.productName);
                ImageView imageFavorited = (ImageView) view.findViewById(R.id.imageRightFavorite);
                if (m_db.getIsFavorite(stDealInfo2.dealId) == 0) {
                    imageFavorited.setImageResource(R.drawable.icon_heartwhite);
                }
                else {
                    imageFavorited.setImageResource(R.drawable.icon_heartred);
                }
                imageFavorited.setTag(stDealInfo2);
                imageFavorited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
                        if (m_db.getIsFavorite(stDealInfo.dealId) == 0) {
                            m_db.setFavorited(stDealInfo.dealId, 1);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartred);
                        }
                        else {
                            m_db.setFavorited(stDealInfo.dealId, 0);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartwhite);
                        }
                    }
                });

                RelativeLayout rlRight = (RelativeLayout) view.findViewById(R.id.rlRightDeal);
                rlRight.setVisibility(View.VISIBLE);
                rlRight.setTag(stDealInfo2);
                rlRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
                        m_db.setTapped(stDealInfo.dealId, 1);
                        Intent intent = new Intent(AllDealsActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT", stDealInfo);
                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                        AllDealsActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
                        startActivity(intent);
                    }
                });
            }

            llContent.addView(view);
        }
    }
}
