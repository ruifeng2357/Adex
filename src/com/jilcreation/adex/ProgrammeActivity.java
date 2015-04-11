package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.jilcreation.utils.GlobalFunc;

public class ProgrammeActivity extends SuperActivity implements View.OnClickListener {
    protected ImageView imageBack;
    protected RelativeLayout rlMain;
    protected RelativeLayout rlCeremony;
    protected RelativeLayout rlShare;
    protected RelativeLayout rlConference;
    protected RelativeLayout rlFlim;
    protected RelativeLayout rlExpo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme);
    }

    @Override
    public void initialize() {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        rlMain.setOnClickListener(this);
        rlCeremony = (RelativeLayout) findViewById(R.id.rlCeremony);
        rlCeremony.setOnClickListener(this);
        rlShare = (RelativeLayout) findViewById(R.id.rlShare);
        rlShare.setOnClickListener(this);
        rlConference = (RelativeLayout) findViewById(R.id.rlConference);
        rlConference.setOnClickListener(this);
        rlFlim = (RelativeLayout) findViewById(R.id.rlFlim);
        rlFlim.setOnClickListener(this);
        rlExpo = (RelativeLayout) findViewById(R.id.rlExpo);
        rlExpo.setOnClickListener(this);
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
        else if (v == rlMain) {
            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
            intent.putExtra("TYPE", ProgrammeListActivity.MODE_MAIN);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);

        }
        else if (v == rlCeremony) {
            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
            intent.putExtra("TYPE", ProgrammeListActivity.MODE_CEREMONY);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);
        }
        else if (v == rlShare) {
            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
            intent.putExtra("TYPE", ProgrammeListActivity.MODE_SHARE);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);
        }
        else if (v == rlConference) {
            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
            intent.putExtra("TYPE", ProgrammeListActivity.MODE_CONFERENCE);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);
        }
        else if (v == rlFlim) {
            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
            intent.putExtra("TYPE", ProgrammeListActivity.MODE_FLIM);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);
        }
        else if (v == rlExpo) {
//            Intent intent = new Intent(ProgrammeActivity.this, ProgrammeListActivity.class);
//            intent.putExtra("TYPE", ProgrammeListActivity.MODE_EXPO);
//            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
//            ProgrammeActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
//            startActivity(intent);
        }
    }
}
