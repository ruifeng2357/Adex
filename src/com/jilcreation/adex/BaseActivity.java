package com.jilcreation.adex;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.jilcreation.utils.ResolutionSet;

/**
 * Created with IntelliJ IDEA.
 * User: RuiFeng
 * Date: 3/12/15
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseActivity extends Activity {
    protected AppPreferences _appPrefs;
    protected RelativeLayout mainLayout;

    protected boolean bInitialized = false;
    public boolean bResumeChecked = false;

    protected boolean isMainScreen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _appPrefs = new AppPreferences(getApplicationContext());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initLayout();
    }

    public void initLayout() {
        // initialize resolution set
        mainLayout = (RelativeLayout)findViewById(getMainLayoutRes());
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        if (bInitialized == false)
                        {
                            Rect r = new Rect();
                            mainLayout.getLocalVisibleRect(r);
                            ResolutionSet._instance.setResolution(r.width(), r.height(), false);
                            ResolutionSet._instance.iterateChild(mainLayout);
                            bInitialized = true;

                            if (bResumeChecked == false)
                                initialize();
                        }
                    }
                }
        );
    }

    protected abstract int getMainLayoutRes();

    public void initialize() {}

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume() { super.onResume(); }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
    }
}