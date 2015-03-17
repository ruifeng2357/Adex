package com.app.adex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rlScan;
    private RelativeLayout rlDeal;
    private RelativeLayout rlEvent;
    private RelativeLayout rlSchedule;
    private RelativeLayout rlPlan;
    private RelativeLayout rlExhibitor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        rlScan = (RelativeLayout) rootView.findViewById(R.id.rlScan);
        rlScan.setOnClickListener(this);
        rlDeal = (RelativeLayout) rootView.findViewById(R.id.rlDeal);
        rlDeal.setOnClickListener(this);
        rlEvent = (RelativeLayout) rootView.findViewById(R.id.rlEvent);
        rlEvent.setOnClickListener(this);
        rlSchedule = (RelativeLayout) rootView.findViewById(R.id.rlSchedule);
        rlSchedule.setOnClickListener(this);
        rlPlan = (RelativeLayout) rootView.findViewById(R.id.rlPlan);
        rlPlan.setOnClickListener(this);
        rlExhibitor = (RelativeLayout) rootView.findViewById(R.id.rlExhibitor);
        rlExhibitor.setOnClickListener(this);

        return rootView;
    }

    @Override
    public int contentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        if ( v == rlScan ) {
            onScan();
        }
        else if ( v == rlDeal ) {
            onDeal();
        }
        else if ( v == rlEvent ) {
            onEvent();
        }
        else if ( v == rlSchedule ) {
            onSchedule();
        }
        else if ( v == rlPlan ) {
            onPlan();
        }
        else if ( v == rlExhibitor ){
            onExhibitor();
        }
    }

    private void onScan() {
        ;
    }

    private void onDeal() {
        ;
    }

    private void onEvent() {
        ;
    }

    private void onSchedule() {
        ;
    }

    private void onPlan() {
        ;
    }

    private void onExhibitor() {
        ;
    }
}