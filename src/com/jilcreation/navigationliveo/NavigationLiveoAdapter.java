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

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jilcreation.adex.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class NavigationLiveoAdapter extends BaseAdapter {

    private int mNewDrawable = 0;
    private int mColorDefault = 0;
	private final Context mcontext;
    private boolean mRemoveAlpha = false;
	private final List<NavigationLiveoItemAdapter> mList;
	private List<NavigationLiveoItemAdapter> mFilteredList;

	public NavigationLiveoAdapter(Context context, List<NavigationLiveoItemAdapter> list, int drawable, int colorDefault, boolean removeAlpha) {
		this.mList = list;
		this.mFilteredList = new ArrayList<NavigationLiveoItemAdapter>();
		Iterator<NavigationLiveoItemAdapter> iterator = this.mList.iterator();
		while (iterator.hasNext()) {
			NavigationLiveoItemAdapter item = iterator.next();
			if (!item.isHidden)
				this.mFilteredList.add(item);
		}

		this.mcontext = context;
        this.mColorDefault = colorDefault;
        this.mNewDrawable = drawable;
        this.mRemoveAlpha = removeAlpha;
	}

	@Override
	public int getCount() {
		return mFilteredList.size();
	}

	@Override
	public NavigationLiveoItemAdapter getItem(int position) {
		return mFilteredList.get(position);
	}

	public int getRealIndexOfPosition(int position) {
		if (position >= mFilteredList.size())
			return 0;
		if (position < 0)
			 return 0;

		NavigationLiveoItemAdapter item = mFilteredList.get(position);
		int index = mList.indexOf(item);
		if (index < 0)
			return 0;
		return index;
	}

	@Override
	public long getItemId(int position) {
		/*
		NavigationLiveoItemAdapter item = mFilteredList.get(position);
		int index = mList.indexOf(item);
		return index;
		*/
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).isHeader ? 0 : 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return !getItem(position).isHeader;
	}

	public void setChecked(int pos, boolean checked) {
		if (pos < 0 || pos > mList.size())
			return;
		if (pos < mList.size())
			mList.get(pos).checked = checked;
		notifyDataSetChanged();			
	}

	public void resetarCheck() {
		for (int i = 0; i < mList.size(); i++) {
			mList.get(i).checked = false;
		}
		this.notifyDataSetChanged();
	}

    class ViewHolder {
		public TextView title;
		public ImageView icon;

		public ViewHolder(){
		}
	}

    private void setAlpha(View v, float alpha) {

        if (!mRemoveAlpha) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                v.setAlpha(alpha);
            } else {
                AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
                animation.setDuration(0);
                animation.setFillAfter(true);
                v.startAnimation(animation);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                v.setAlpha(1f);
            } else {
                AlphaAnimation animation = new AlphaAnimation(1f, 1f);
                animation.setDuration(0);
                animation.setFillAfter(true);
                v.startAnimation(animation);
            }
        }
    }

	public View getView(int position, View convertView, ViewGroup parent) {

		NavigationLiveoItemAdapter item = mFilteredList.get(position);
		ViewHolder holder;
		
		if (convertView == null) {
			holder = new ViewHolder();
			
			int layout = ((item.isHeader) ? (item.title != null && !item.title.trim().equals(""))?R.layout.navigation_list_item_sub_header:R.layout.navigation_list_item_sub_header_line
					                      : (item.icon != 0) ? R.layout.navigation_list_item_icon :R.layout.navigation_list_item);
			
			convertView = LayoutInflater.from(mcontext).inflate(layout, null);			
			
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		if (holder.title != null){
			holder.title.setText(item.title);

            if (!item.isHeader) {
                setAlpha(holder.title, (item.checked ? 1f : 0.87f));

                holder.title.setTextColor((!item.isHeader && item.checked && item.colorSelected > 0 ?
                        mcontext.getResources().getColor(item.colorSelected) :
                        mcontext.getResources().getColor(android.R.color.black)));
            }
		}

		if (holder.icon != null) {
			if (item.icon != 0) {
				holder.icon.setVisibility(View.VISIBLE);
				holder.icon.setImageResource(item.icon);
                setAlpha(holder.icon, (!item.isHeader && item.checked ? 1f : 0.54f));

                holder.icon.setColorFilter((!item.isHeader && item.checked && item.colorSelected > 0 ?
                        mcontext.getResources().getColor(item.colorSelected) :
                        (mColorDefault != 0 ? mcontext.getResources().getColor(mColorDefault) :
                                           mcontext.getResources().getColor(android.R.color.black))));
			} else {
				holder.icon.setVisibility(View.GONE);
			}
		}
	
		if (!item.isHeader) {			
			if (item.checked) {
                convertView.setBackgroundResource((!item.removeSelector ? ( mNewDrawable == 0 ? R.drawable.selector_check_item_navigation : mNewDrawable) : R.drawable.selector_no_check_item_navigation));
			} else {
                convertView.setBackgroundResource(R.drawable.selector_no_check_item_navigation);
			}
		}

	    return convertView;		
	}
}
