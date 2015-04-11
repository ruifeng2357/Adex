package com.jilcreation.adex;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class LuckyDrawFragment extends BaseFragment implements View.OnClickListener {
    private final String WEBSITE_ADDRESS = "http://jilcreation.com/adexcontest/luckydrawresult.php";
    protected RotateAnimation _rotateAnimation;

    protected ImageView ivProgress;
    protected ImageView imageMenuTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        _rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        _rotateAnimation.setInterpolator(new LinearInterpolator());
        _rotateAnimation.setDuration(1000);
        _rotateAnimation.setRepeatCount(Animation.INFINITE);
        _rotateAnimation.setFillEnabled(true);
        _rotateAnimation.setFillAfter(true);

        ivProgress = (ImageView) rootView.findViewById(R.id.ivProgress);
        ivProgress.startAnimation(_rotateAnimation);
        imageMenuTab = (ImageView) rootView.findViewById(R.id.imageMenuTab);
        imageMenuTab.setOnClickListener(this);

        WebView webView = (WebView) rootView.findViewById(R.id.webBrowser);
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(WEBSITE_ADDRESS);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {}

            @Override
            public void onPageFinished(WebView view, String url) {
                ivProgress.clearAnimation();
                ivProgress.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { return false; }
        });

        return rootView;
    }

    @Override
    public int contentLayout() {
        return R.layout.fragment_lucky;
    }

    @Override
    public void onClick(View v) {
        if ( v == imageMenuTab ) {
            MainMenuActivity activity = (MainMenuActivity)getActivity();
            activity.openDrawer();
        }
    }
}