package com.jilcreation.adex;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.jilcreation.define.AdexConstans;
import com.jilcreation.model.STDealInfo;
import com.jilcreation.model.modelmanage.SQLiteDBHelper;
import com.jilcreation.ui.SmartImageView.SmartImageView;
import com.jilcreation.utils.GlobalFunc;
import com.jilcreation.utils.ResolutionSet;
import java.util.ArrayList;
import java.util.List;

public class NearbyActivity extends SuperActivity implements View.OnClickListener {
    private static final int BEACONCON_TIMEOUT = 10 * 1000;
    private static final int REQUEST_ENABLE_BT = 1234;

    private static final String BEACON_UUID = "236C55B3-0D4A-4976-A7D7-C8B9DA24894D";
    private static final int BEACON_MAJOR = 8;

    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", BEACON_UUID, BEACON_MAJOR, null);
    private static final String TAG = "BLE Module";

    protected RelativeLayout rlBack;
    protected ImageView imageBack;
    protected LinearLayout llContent;
    protected LinearLayout llSearch;
    protected EditText editSearch;
    protected TextView textAllDeals;

    ArrayList<STDealInfo> arrDealInfos = new ArrayList<STDealInfo>();

    SQLiteDBHelper m_db = null;

    private BeaconManager beaconManager;

    private ArrayList<DetectedBeacon> detectedBeacons = new ArrayList<DetectedBeacon>();
    private class DetectedBeacon {
        Beacon _beacon;
        long _lastDetectTime;

        public DetectedBeacon() {
            _beacon = null;
            _lastDetectTime = 0;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        arrDealInfos = getIntent().getParcelableArrayListExtra("DEALLIST");

        m_db = new SQLiteDBHelper(this);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(final Region region, final List<Beacon> beacons) {
                Region temp = region;
                String str = temp.getProximityUUID();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<STDealInfo> arrDispDeals = new ArrayList<STDealInfo>();
                        /*
                        * remove timeout beacons
                         */
                        long nCurrentTime = System.currentTimeMillis();
                        ArrayList<DetectedBeacon> tempBeacons = new ArrayList<DetectedBeacon>();
                        for (int i = 0; i < detectedBeacons.size(); i++) {
                            if ( ( nCurrentTime - detectedBeacons.get(i)._lastDetectTime ) < BEACONCON_TIMEOUT )
                                tempBeacons.add(detectedBeacons.get(i));
                        }
                        detectedBeacons = tempBeacons;

                        if (beacons.size() > 0) {
                            for ( int i = 0; i < beacons.size(); i++ ) {
                                Beacon beacon = beacons.get(i);

                                int nPos = 0;
                                boolean isAddedBeacon  = false;
                                for ( nPos = 0; nPos < detectedBeacons.size(); nPos++ ) {
                                    if (detectedBeacons.get(nPos)._beacon.getMinor() == beacon.getMinor()) {
                                        isAddedBeacon = true;
                                        detectedBeacons.get(nPos)._lastDetectTime = System.currentTimeMillis();
                                        break;
                                    }
                                }

                                if (isAddedBeacon == false) {
                                    DetectedBeacon newBeacon = new DetectedBeacon();
                                    newBeacon._beacon = beacon;
                                    newBeacon._lastDetectTime = System.currentTimeMillis();

                                    detectedBeacons.add(newBeacon);
                                }
                            }
                        }

                        for (int i = 0; i < detectedBeacons.size(); i++ ) {
                            for (int j = 0; j < arrDealInfos.size(); j++) {
                                if ( detectedBeacons.get(i)._beacon.getMinor() == arrDealInfos.get(j).merchantId) {
                                    arrDispDeals.add(arrDealInfos.get(j));
                                    break;
                                }
                            }
                        }

                        showDeals(arrDispDeals);
                    }
                });
            }
        });
    }

    @Override
    public void initialize() {
        rlBack = (RelativeLayout) findViewById(R.id.rlBack);
        rlBack.setOnClickListener(this);

        textAllDeals = (TextView) findViewById(R.id.textAllDeals);
        textAllDeals.setOnClickListener(this);
        editSearch = (EditText) findViewById(R.id.editSearch);

        llContent = (LinearLayout) findViewById(R.id.llContent);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llSearch.setOnClickListener(this);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);
    }

    @Override
    public int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
        }
    }

    @Override
    public void onStop() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {}

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        beaconManager.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connectToService() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Toast.makeText(NearbyActivity.this, "Cannot start ranging, something terrible happened", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if ( v == rlBack ) {
            finish();
        }
        else if ( v == imageBack ) {
            finish();
        }
        else if ( v == llSearch ) {
//            String szSearch = editSearch.getText().toString().toLowerCase();
//            ArrayList<STDealInfo> arrayList = new ArrayList<STDealInfo>();
//
//            if (szSearch.length() == 0) {
//                arrayList = arrDealInfos;
//            }
//            else {
//                for ( int i = 0; i < arrDealInfos.size(); i++ ) {
//                    if (arrDealInfos.get(i).productName.toLowerCase().contains(szSearch)) {
//                        arrayList.add(arrDealInfos.get(i));
//                    }
//                }
//            }
//
//            showDeals(arrayList);
        }
        else if ( textAllDeals == v ) {
            Intent intent = new Intent(this, AllDealsActivity.class);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            startActivity(intent);
            finish();
        }
    }

    private void showDeals(ArrayList<STDealInfo> arrList) {
        llContent.removeAllViews();

        int nCount = arrList.size();

        for (int i = 0; i < (nCount + 1) / 2 ; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_dealitem, null);
            ResolutionSet._instance.iterateChild(view);

            STDealInfo stDealInfo1 = new STDealInfo();
            STDealInfo stDealInfo2 = new STDealInfo();
            try {
                stDealInfo1 = arrList.get(i * 2);
            } catch (Exception ex) {
                stDealInfo1 = null;
            }
            try {
                stDealInfo2 = arrList.get(i * 2 + 1);
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
                        Intent intent = new Intent(NearbyActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT", stDealInfo);
                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                        NearbyActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
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
                        Intent intent = new Intent(NearbyActivity.this, ProductDetailActivity.class);
                        intent.putExtra("PRODUCT", stDealInfo);
                        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
                        NearbyActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
                        startActivity(intent);
                    }
                });
            }

            llContent.addView(view);
        }
    }
}
