/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jilcreation.navigationliveo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.jilcreation.adex.R;
import java.util.List;

public abstract class NavigationLiveo extends FragmentActivity {
    private ListView mList;
    private View mHeader;

    private int mColorDefault = 0;
    private int mColorSelected = 0;
    private int mCurrentPosition = 0;
    private int mNewSelector = 0;
    private boolean mRemoveAlpha = false;
    private boolean mRemoveSelector = false;

    private List<Integer> mListIcon;
    private List<Integer> mListHeader;
    private List<Integer> mListHidden;
    private List<String> mListNameItem;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mRelativeDrawer;

    protected NavigationLiveoAdapter mNavigationAdapter;
    protected NavigationLiveoListener mNavigationListener;

    protected int mContainerLayoutId;

    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    public static final String CURRENT_TEAM = "CURRENT_TEAM";

    /**
     * onCreate(Bundle savedInstanceState).
     * @param savedInstanceState onCreate(Bundle savedInstanceState).
     */
    public abstract void onInt(Bundle savedInstanceState);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_parent);

        mList = (ListView) findViewById(R.id.list);
        mList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerShadow(R.drawable.navbar_shadow, GravityCompat.START);

        mRelativeDrawer = (FrameLayout) this.findViewById(R.id.relativeDrawer);

        mContainerLayoutId = R.id.container;

		if (mList != null) {
            mountListNavigation(savedInstanceState);
		}

		if (savedInstanceState != null) {
			setCurrentPosition(savedInstanceState.getInt(CURRENT_POSITION));
	    }else{
            mCurrentPosition = getIntent().getIntExtra(CURRENT_POSITION, mCurrentPosition);
            mNavigationListener.onItemClickNavigation(mCurrentPosition, mContainerLayoutId);
	    }

        setCheckedItemNavigation(mCurrentPosition, true);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub		
		super.onSaveInstanceState(outState);		
		outState.putInt(CURRENT_POSITION, mCurrentPosition);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
    	mNavigationListener.onPrepareOptionsMenuNavigation(menu, mCurrentPosition, drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	 }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int finalPosition = position - 1;
            mDrawerLayout.closeDrawer(mRelativeDrawer);

            Handler hd = new Handler(getMainLooper());
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int itemPosition = mNavigationAdapter.getRealIndexOfPosition(finalPosition);
                    mNavigationListener.onItemClickNavigation(itemPosition, mContainerLayoutId);
                    setCurrentPosition(finalPosition);
                    setCheckedItemNavigation(finalPosition, true);
                }
            }, 250);
        }
    }

    private void mountListNavigation(Bundle savedInstanceState){
        createUserDefaultHeader();
        onInt(savedInstanceState);
        setAdapterNavigation();
    }

    private void setAdapterNavigation(){

        if (mNavigationListener == null){
            throw new RuntimeException("You must start the NavigationListener in onInit() method of its main activity. Example: this.setNavigationListener(this);");
        }

        mNavigationAdapter = new NavigationLiveoAdapter(this, NavigationLiveoList.getNavigationAdapter(
                mListNameItem,
                mListIcon,
                mListHeader,
                mListHidden,
                mColorSelected,
                mRemoveSelector), mNewSelector, mColorDefault, mRemoveAlpha);

        mList.setAdapter(mNavigationAdapter);
    }

    private void resetAdpterNavigation(View v) {
        mNavigationAdapter = null;
        mList.setAdapter(mNavigationAdapter);
        mList.addHeaderView(v, null, false);
        setAdapterNavigation();

        setCheckedItemNavigation(mCurrentPosition, true);
    }

    /**
     * Create user default header
     */
    private void createUserDefaultHeader() {
        mHeader = getLayoutInflater().inflate(R.layout.navigation_list_header, mList, false);
        mList.addHeaderView(mHeader);
    }

    /**
     * Set adapter attributes
     * @param listNameItem list name item.
     * @param listIcon list icon item.
     * @param listItensHeader list header name item.
     */
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon, List<Integer> listItensHeader,
                                     List<Integer> listHidden){
        this.mListNameItem = listNameItem;
        this.mListIcon = listIcon;
        this.mListHeader = listItensHeader;
        this.mListHidden = listHidden;
    }

    /**
     * Set adapter attributes
     * @param listNameItem list name item.
     * @param listIcon list icon item.
     */
    public void setNavigationAdapter(List<String> listNameItem, List<Integer> listIcon){
        this.mListNameItem = listNameItem;
        this.mListIcon = listIcon;
    }

    /**
     * Starting listener navigation
     * @param navigationListener listener.
     */
    public void setNavigationListener(NavigationLiveoListener navigationListener){
        this.mNavigationListener = navigationListener;
    };

    /**
     * First item of the position selected from the list
     * @param position ...
     */
    public void setDefaultStartPositionNavigation(int position){
        this.mCurrentPosition = position;
    }

    /**
     * Position in the last clicked item list
     * @param position ...
     */
    public void setCurrentPosition(int position){
        this.mCurrentPosition = position;
    }

    /**
     * get position in the last clicked item list
     */
    public int getCurrentPosition(){
        return this.mCurrentPosition;
    }

    /*{  }*/

    /**
     * Select item clicked
     * @param position item position.
     * @param checked true to check.
     */
    public void setCheckedItemNavigation(int position, boolean checked){
        this.mNavigationAdapter.resetarCheck();
        this.mNavigationAdapter.setChecked(position, checked);
    }

    /**
     * Information footer list item
     * @param title item footer name.
     */
    public void setFooterInformationDrawer(String title){

        if (title == null){
            throw new RuntimeException("The title can not be null or empty");
        }

        if (title.trim().equals("")){
            throw new RuntimeException("The title can not be null or empty");
        }
    };

    /**
     * Information footer list item
     * @param title item footer name.
     */
    public void setFooterInformationDrawer(int title){

        if (title == 0){
            throw new RuntimeException("The title can not be null or empty");
        }
    };

    /**
     * Item color selected in the list - name and icon (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public void setColorSelectedItemNavigation(int colorId){
        this.mColorSelected = colorId;
    }

    /**
     * Item color default in the list - name and icon (use before the setNavigationAdapter)
     * @param colorId color id.
     */
    public void setColorDefaultItemNavigation(int colorId){
        this.mColorDefault = colorId;
    }

    /**
     * New selector navigation
     * @param drawable drawable xml - selector.
     */
    public void setNewSelectorNavigation(int drawable){

        if (mRemoveSelector){
            throw new RuntimeException("The option to remove the select is active. Please remove the removeSelectorNavigation method so you can use the setNewSelectorNavigation");
        }

        this.mNewSelector = drawable;
    }

    /**
     * Remove selector navigation
     */
    public void removeSelectorNavigation(){
        this.mRemoveSelector = true;
    }


    /**
     * Remove alpha item navigation (use before the setNavigationAdapter)
     */
    public void removeAlphaItemNavigation(){
        this.mRemoveAlpha = !mRemoveAlpha;
    }

    /**
     * Remove default Header
     */
    public void showDefauldHeader() {
        if (mHeader == null){
            throw new RuntimeException("header was not created");
        }

        resetAdpterNavigation(mHeader);
    }

    /**
     * Remove default Header
     */
    private void removeDefauldHeader() {
        if (mHeader == null){
            throw new RuntimeException("header was not created");
        }

        mList.removeHeaderView(mHeader);
    }

    /**
     * Add custom Header
     * @param v ...
     */
    public void addCustomHeader(View v) {
        if (v == null){
            throw new RuntimeException("header custom was not created");
        }

        removeDefauldHeader();
        resetAdpterNavigation(v);
    }

    /**
     * Remove default Header
     * @param v ...
     */
    public void removeCustomdHeader(View v) {
        if (v == null){
            throw new RuntimeException("header custom was not created");
        }

        mList.removeHeaderView(v);
    }

    /**
     * get listview
     */
    public ListView getListView() {
        return this.mList;
    }

    /**
     * Open drawer
     */
    public void openDrawer() {
        mDrawerLayout.openDrawer(mRelativeDrawer);
    }

    /**
     * Close drawer
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mRelativeDrawer);
    }

    @Override
    public void onBackPressed() {

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
        if (drawerOpen) {
            mDrawerLayout.closeDrawer(mRelativeDrawer);
        } else {
            super.onBackPressed();
        }
    }
}
