package com.app.adex;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.app.utils.ResolutionSet;

public abstract class BaseFragment extends Fragment {
    protected View mainLayout = null;
    protected boolean bInitialized = false;

    protected LayoutInflater _inflater;

    public boolean isInitialized() {
        return bInitialized;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = setContentLayout(inflater, container, contentLayout());
        _inflater = inflater;

        mainLayout = rootView.findViewById(R.id.rlParent);
        ResolutionSet._instance.iterateChild(mainLayout);
//        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    public void onGlobalLayout() {
//                        if (bInitialized == false) {
//                            Rect r = new Rect();
//                            mainLayout.getLocalVisibleRect(r);
//                            ResolutionSet._instance.setResolution(r.width(), r.height(), true);
//                            ResolutionSet._instance.iterateChild(mainLayout);
//                            bInitialized = true;
//                        }
//                    }
//                }
//        );

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public View setContentLayout(LayoutInflater inflater, ViewGroup container, int layout) {
        View rootView = inflater.inflate(layout, container, false);

        return rootView;
    }

    public abstract int contentLayout();
}