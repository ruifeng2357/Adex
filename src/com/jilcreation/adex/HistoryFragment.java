package com.jilcreation.adex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jilcreation.model.STHisFavInfo;
import com.jilcreation.model.modelmanage.SQLiteDBHelper;
import com.jilcreation.ui.SmartImageView.SmartImageView;
import com.jilcreation.utils.GlobalFunc;
import com.jilcreation.utils.ResolutionSet;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment implements View.OnClickListener {
    private ArrayList<STHisFavInfo> arrayList = null;
    private SQLiteDBHelper m_db = null;

    protected ImageView imageMenuTab;
    protected LinearLayout llContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        m_db = new SQLiteDBHelper(getActivity());
        arrayList = m_db.getHistoryList();

        imageMenuTab = (ImageView) rootView.findViewById(R.id.imageMenuTab);
        imageMenuTab.setOnClickListener(this);
        llContent = (LinearLayout) rootView.findViewById(R.id.llContent);

        showHistory();

        return rootView;
    }

    @Override
    public int contentLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void onClick(View v) {
        if ( v == imageMenuTab ) {
            MainMenuActivity activity = (MainMenuActivity)getActivity();
            activity.openDrawer();
        }
    }

    private void showHistory() {
        llContent.removeAllViews();

        int nCount = arrayList.size();

        for (int i = 0; i < (nCount + 1) / 2 ; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_hisfavitem, null);
            ResolutionSet._instance.iterateChild(view);

            STHisFavInfo stHisFavInfo1 = new STHisFavInfo();
            STHisFavInfo stHisFavInfo2 = new STHisFavInfo();
            try {
                stHisFavInfo1 = arrayList.get(i * 2);
            } catch (Exception ex) {
                stHisFavInfo1 = null;
            }
            try {
                stHisFavInfo2 = arrayList.get(i * 2 + 1);
            } catch (Exception ex) {
                stHisFavInfo2 = null;
            }

            if (stHisFavInfo1 != null) {
                SmartImageView imageLeft = (SmartImageView)view.findViewById(R.id.imageLeftDeal);
                if (m_db.getLocImgPath(stHisFavInfo1.dealId).length() > 0) {
                    try {
                        imageLeft.setImageBitmap(GlobalFunc.getBitmapFromLocalpath(m_db.getLocImgPath(stHisFavInfo1.dealId)));
                    } catch (Exception ex) {
                        imageLeft.setImageUrl(stHisFavInfo1.imgUrl);
                    }
                } else {
                    imageLeft.setImageUrl(stHisFavInfo1.imgUrl);
                    GlobalFunc.saveProductImage(getActivity(), stHisFavInfo1.dealId, stHisFavInfo1.imgUrl);
                }
                TextView textBrand = (TextView) view.findViewById(R.id.textLeftBrand);
                textBrand.setText(stHisFavInfo1.brandName);
                TextView textName = (TextView) view.findViewById(R.id.textLeftName);
                textName.setText(stHisFavInfo1.productName);
                ImageView imageFavorited = (ImageView) view.findViewById(R.id.imageLeftFavorite);
                if (m_db.getIsFavorite(stHisFavInfo1.dealId) == 0) {
                    imageFavorited.setImageResource(R.drawable.icon_heartwhite);
                }
                else {
                    imageFavorited.setImageResource(R.drawable.icon_heartred);
                }
                imageFavorited.setTag(stHisFavInfo1);
                imageFavorited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STHisFavInfo stHisFavInfo = (STHisFavInfo) v.getTag();
                        if (m_db.getIsFavorite(stHisFavInfo.dealId) == 0) {
                            m_db.setFavorited(stHisFavInfo.dealId, 1);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartred);
                        }
                        else {
                            m_db.setFavorited(stHisFavInfo.dealId, 0);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartwhite);
                        }
                    }
                });

                RelativeLayout rlLeft = (RelativeLayout) view.findViewById(R.id.rlLeftDeal);
                rlLeft.setVisibility(View.VISIBLE);
//                rlLeft.setTag(stDealInfo1);
//                rlLeft.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
//                        m_db.setTapped(stDealInfo.dealId, 1);
//                        Intent intent = new Intent(AllDealsActivity.this, ProductDetailActivity.class);
//                        intent.putExtra("PRODUCT", stDealInfo);
//                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
//                        AllDealsActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
//                        startActivity(intent);
//                    }
//                });
            }
            if (stHisFavInfo2 != null) {
                SmartImageView imageLeft = (SmartImageView)view.findViewById(R.id.imageRightDeal);
                if (m_db.getLocImgPath(stHisFavInfo2.dealId).length() > 0) {
                    try {
                        imageLeft.setImageBitmap(GlobalFunc.getBitmapFromLocalpath(m_db.getLocImgPath(stHisFavInfo2.dealId)));
                    } catch (Exception ex) {
                        imageLeft.setImageUrl(stHisFavInfo2.imgUrl);
                    }
                } else {
                    imageLeft.setImageUrl(stHisFavInfo2.imgUrl);
                    GlobalFunc.saveProductImage(getActivity(), stHisFavInfo2.dealId, stHisFavInfo2.imgUrl);
                }
                TextView textBrand = (TextView) view.findViewById(R.id.textRightBrand);
                textBrand.setText(stHisFavInfo2.brandName);
                TextView textName = (TextView) view.findViewById(R.id.textRightName);
                textName.setText(stHisFavInfo2.productName);
                ImageView imageFavorited = (ImageView) view.findViewById(R.id.imageRightFavorite);
                if (m_db.getIsFavorite(stHisFavInfo2.dealId) == 0) {
                    imageFavorited.setImageResource(R.drawable.icon_heartwhite);
                }
                else {
                    imageFavorited.setImageResource(R.drawable.icon_heartred);
                }
                imageFavorited.setTag(stHisFavInfo2);
                imageFavorited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        STHisFavInfo stHisFavInfo = (STHisFavInfo) v.getTag();
                        if (m_db.getIsFavorite(stHisFavInfo.dealId) == 0) {
                            m_db.setFavorited(stHisFavInfo.dealId, 1);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartred);
                        }
                        else {
                            m_db.setFavorited(stHisFavInfo.dealId, 0);
                            ((ImageView)v).setImageResource(R.drawable.icon_heartwhite);
                        }
                    }
                });

                RelativeLayout rlRight = (RelativeLayout) view.findViewById(R.id.rlRightDeal);
                rlRight.setVisibility(View.VISIBLE);
//                rlRight.setTag(stDealInfo2);
//                rlRight.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        STDealInfo stDealInfo = (STDealInfo) v.getTag();
//                        m_db.setTapped(stDealInfo.dealId, 1);
//                        Intent intent = new Intent(AllDealsActivity.this, ProductDetailActivity.class);
//                        intent.putExtra("PRODUCT", stDealInfo);
//                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
//                        AllDealsActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
//                        startActivity(intent);
//                    }
//                });
            }

            llContent.addView(view);
        }
    }
}