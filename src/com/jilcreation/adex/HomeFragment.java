package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.jilcreation.utils.GlobalFunc;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rlScan;
    private RelativeLayout rlDeal;
    private RelativeLayout rlEvent;
    private RelativeLayout rlSchedule;
    private RelativeLayout rlPlan;
    private RelativeLayout rlExhibitor;
    private ImageView imageMenuTab;

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

        imageMenuTab = (ImageView) rootView.findViewById(R.id.imageMenuTab);
        imageMenuTab.setOnClickListener(this);

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
        else if ( v == imageMenuTab ) {
            MainMenuActivity activity = (MainMenuActivity)getActivity();
            activity.openDrawer();
        }
    }

    private void onScan() {
        Intent intent = new Intent(getActivity(), AdexScanActivity.class);
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        getActivity().getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        startActivity(intent);
    }

    private void onDeal() {
        Intent intent = new Intent(getActivity(), AllDealsActivity.class);
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        getActivity().getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        startActivity(intent);
    }

    private void onEvent() {
        ;
    }

    private void onSchedule() {
        Intent intent = new Intent(getActivity(), ProgrammeActivity.class);
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        getActivity().getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        startActivity(intent);
    }

    private void onPlan() {
        Intent intent = new Intent(getActivity(), PlanActivity.class);
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        getActivity().getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        startActivity(intent);
    }

    private void onExhibitor() {
        Intent intent = new Intent(getActivity(), ExhibitorActivity.class);
        intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
        getActivity().getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
        startActivity(intent);
    }
}