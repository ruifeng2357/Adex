package com.jilcreation.adex;

import android.os.Bundle;

public class PlanActivity extends SuperActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }

    @Override
    public void initialize() {
    }

    @Override
    public int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
