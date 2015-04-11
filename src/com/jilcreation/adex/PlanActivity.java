package com.jilcreation.adex;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.jilcreation.utils.ResolutionSet;

public class PlanActivity extends SuperActivity {
    RelativeLayout mainLayout;
    boolean bInitialized = false;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mainLayout = (RelativeLayout)findViewById(getMainLayoutRes());
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (bInitialized == false)
                        {
                            Rect r = new Rect();
                            mainLayout.getLocalVisibleRect(r);
                            ResolutionSet._instance.setResolution(r.width(), r.height(), true);
                            ResolutionSet._instance.iterateChild(mainLayout);
                            bInitialized = true;

                            initialize();
                        }
                    }
                }
        );
    }

    @Override
    protected int getMainLayoutRes() {
        return R.id.rlParent;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void initialize() {}
}
