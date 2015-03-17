package com.jilcreation.adex;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jilcreation.model.STDealInfo;
import com.jilcreation.ui.SmartImageView.SmartImageView;

public class ProductDetailActivity extends SuperActivity implements View.OnClickListener {
    STDealInfo mDealInfo = new STDealInfo();

    protected SmartImageView imagePhoto;
    protected TextView textBrand;
    protected TextView textName;
    protected TextView textCategory;
    protected TextView textModel;
    protected TextView textPrice;
    protected TextView textDetail;
    protected RelativeLayout rlBack;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        mDealInfo = getIntent().getParcelableExtra("PRODUCT");
        if (mDealInfo == null) {
            mDealInfo = new STDealInfo();
        }
    }

    @Override
    public void initialize() {
        imagePhoto = (SmartImageView) findViewById(R.id.imageProduct);
        imagePhoto.setImageUrl(mDealInfo.imageUrl);
        textBrand = (TextView) findViewById(R.id.textBrand);
        textBrand.setText(mDealInfo.productBrand);
        textName = (TextView) findViewById(R.id.textName);
        textName.setText(mDealInfo.productName);
        textCategory = (TextView) findViewById(R.id.textCategory);
        textCategory.setText(mDealInfo.category);
        textModel = (TextView) findViewById(R.id.textModel);
        textPrice = (TextView) findViewById(R.id.textPrice);
        String szPrice = String.format("S$%.2f", mDealInfo.price);
        textPrice.setText(szPrice);
        textDetail = (TextView) findViewById(R.id.textDetail);
        textDetail.setText(mDealInfo.detail);

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
    public void onClick(View v) {
        if (v == rlBack) {
            finish();
        }
    }
}
