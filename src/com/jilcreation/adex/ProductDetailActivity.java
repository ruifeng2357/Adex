package com.jilcreation.adex;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jilcreation.model.STDealInfo;
import com.jilcreation.model.modelmanage.SQLiteDBHelper;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.ui.SmartImageView.SmartImageView;
import com.jilcreation.utils.GlobalFunc;
import org.w3c.dom.Text;

public class ProductDetailActivity extends SuperActivity implements View.OnClickListener {
    STDealInfo mDealInfo = new STDealInfo();
    private long startTime = 0;

    protected SmartImageView imagePhoto;
    protected TextView textMerchantName;
    protected TextView textBooth;
    protected TextView textBrand;
    protected TextView textName;
    protected TextView textPrice;
    protected TextView textDetail;
    protected RelativeLayout rlBack;
    protected ImageView imageFavorited;
    protected ImageView imageBack;

    private SQLiteDBHelper m_db = null;
    private AppPreferences appPreferences;
    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);
        }
        @Override
        public void onFailure(Throwable error, String content) {
            super.onFailure(error, content);
        }
    };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        startTime = System.currentTimeMillis();
        m_db = new SQLiteDBHelper(this);
        appPreferences = new AppPreferences(this);

        mDealInfo = getIntent().getParcelableExtra("PRODUCT");
        if (mDealInfo == null) {
            mDealInfo = new STDealInfo();
        }
    }

    @Override
    public void initialize() {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);
        imagePhoto = (SmartImageView) findViewById(R.id.imageProduct);
        if (m_db.getLocImgPath(mDealInfo.dealId).length() > 0) {
            try {
                imagePhoto.setImageBitmap(GlobalFunc.getBitmapFromLocalpath(m_db.getLocImgPath(mDealInfo.dealId)));
            } catch (Exception ex) {
                imagePhoto.setImageUrl(mDealInfo.imageUrl);
            }
        } else {
            imagePhoto.setImageUrl(mDealInfo.imageUrl);
            GlobalFunc.saveProductImage(ProductDetailActivity.this, mDealInfo.dealId, mDealInfo.imageUrl);
        }
        textMerchantName = (TextView) findViewById(R.id.textMerchantName);
        textMerchantName.setText(mDealInfo.merchantName);
        textBooth = (TextView) findViewById(R.id.textBooth);
        textBooth.setText(mDealInfo.booth);
        textBrand = (TextView) findViewById(R.id.textBrand);
        textBrand.setText(mDealInfo.productBrand);
        textName = (TextView) findViewById(R.id.textProductName);
        textName.setText(mDealInfo.productName);
        textPrice = (TextView) findViewById(R.id.textPrice);
        String szPrice = String.format("S$%.2f", mDealInfo.price);
        textPrice.setText(szPrice);
        textDetail = (TextView) findViewById(R.id.textDetail);
        textDetail.setText(mDealInfo.detail);
        imageFavorited = (ImageView) findViewById(R.id.imageFavorite);
        if (m_db.getIsFavorite(mDealInfo.dealId) == 0) {
            imageFavorited.setImageResource(R.drawable.icon_heartwhite);
        }
        else {
            imageFavorited.setImageResource(R.drawable.icon_heartred);
        }
        imageFavorited.setOnClickListener(this);

        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnClickListener(this);
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
    public void onStop() {
        super.onStop();

        long duration = System.currentTimeMillis() - startTime;
        ServerManager.updateDuration( handler, appPreferences.getUserId(), mDealInfo.dealId, duration);
    }

    @Override
    public void onClick(View v) {
        if (v == rlBack) {
            finish();
        }
        else if ( v == imageBack ) {
            finish();
        }
        else if (v == imageFavorited) {
            if (m_db.getIsFavorite(mDealInfo.dealId) == 0) {
                m_db.setFavorited(mDealInfo.dealId, 1);
                imageFavorited.setImageResource(R.drawable.icon_heartred);
            }
            else {
                m_db.setFavorited(mDealInfo.dealId, 0);
                imageFavorited.setImageResource(R.drawable.icon_heartwhite);
            }
        }
    }
}
