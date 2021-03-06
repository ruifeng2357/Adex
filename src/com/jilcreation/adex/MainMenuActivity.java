package com.jilcreation.adex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import com.jilcreation.navigationliveo.NavigationLiveo;
import com.jilcreation.navigationliveo.NavigationLiveoListener;
import com.jilcreation.utils.GlobalFunc;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends NavigationLiveo implements NavigationLiveoListener {
    public static final int INDEX_HOME = 0;
    public static final int INDEX_HISTORY = 1;
    public static final int INDEX_FAVOURITE = 2;
    public static final int LUCKY_DRAW = 3;
    public static final int INDEX_SETTINGS = 4;

    public List<String> mListNameItem;

    Fragment _currentFragment = null;
    Fragment _homeFragment = null;
    Fragment _luckFragment = null;
    Fragment _historyFragment = null;
    Fragment _favouriteFragment = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInt(Bundle savedInstanceState) {
        this.setNavigationListener(this);

        // name of the list items
        mListNameItem = new ArrayList<String>();
        mListNameItem.add(0, "Home");
        mListNameItem.add(1, "History");
        mListNameItem.add(2, "Favourite");
        mListNameItem.add(3, "LuckyDraw Result");
        mListNameItem.add(4, "Settings");

        // icons list items
        List<Integer> mListIconItem = new ArrayList<Integer>();
        mListIconItem.add(0, 0);
        mListIconItem.add(1, 0);
        mListIconItem.add(2, 0);
        mListIconItem.add(3, 0);
        mListIconItem.add(4, 0);

        setColorSelectedItemNavigation(R.color.adex_tintcolor);

        this.setNavigationAdapter(mListNameItem, mListIconItem, null, null);

        int nIndex = getIntent().getIntExtra("SELINDEX", 0);
        onItemClickNavigation(nIndex, R.id.container);
        setCurrentPosition(nIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        int nDir = getIntent().getIntExtra(GlobalFunc.ANIM_DIRECTION(), -1);
        if (nDir == GlobalFunc.ANIM_COVER_FROM_LEFT())
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        else if (nDir == GlobalFunc.ANIM_COVER_FROM_RIGHT())
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        else
            overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        if (_currentFragment == _homeFragment) {
            finish();
        }
    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (position == INDEX_HOME) {
            if (_homeFragment == null) {
                _homeFragment = new HomeFragment();
            }
            if (_currentFragment != _homeFragment) {
                fragmentManager.beginTransaction().replace(layoutContainerId, _homeFragment).commit();
                _currentFragment = _homeFragment;
            }
        }
        else if (position == INDEX_HISTORY) {
            if (_historyFragment == null) {
                _historyFragment = new HistoryFragment();
            }
            if (_currentFragment != _historyFragment) {
                fragmentManager.beginTransaction().replace(layoutContainerId, _historyFragment).commit();
                _currentFragment = _historyFragment;
            }
        }
        else if (position == INDEX_FAVOURITE) {
            if (_favouriteFragment == null) {
                _favouriteFragment = new FavouriteFragment();
            }
            if (_currentFragment != _favouriteFragment) {
                fragmentManager.beginTransaction().replace(layoutContainerId, _favouriteFragment).commit();
                _currentFragment = _favouriteFragment;
            }
        }
        else if (position == LUCKY_DRAW) {
            if (_luckFragment == null) {
                _luckFragment = new LuckyDrawFragment();
            }
            if (_currentFragment != _luckFragment) {
                fragmentManager.beginTransaction().replace(layoutContainerId, _luckFragment).commit();
                _currentFragment = _luckFragment;
            }
        }
        else if (position == INDEX_SETTINGS) {
            Intent intent = new Intent(MainMenuActivity.this, SettingActivity.class);
            intent.putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_LEFT());
            MainMenuActivity.this.getIntent().putExtra(GlobalFunc.ANIM_DIRECTION(), GlobalFunc.ANIM_COVER_FROM_RIGHT());
            startActivity(intent);
        }
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

    }

    @Override
    public void onClickFooterItemNavigation(View v) {

    }

    @Override
    public void onClickUserPhotoNavigation(View v) {

    }
}
