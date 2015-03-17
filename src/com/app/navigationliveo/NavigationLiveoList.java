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

package com.app.navigationliveo;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

public class NavigationLiveoList {

    public static List<NavigationLiveoItemAdapter> getNavigationAdapter(List<String> listNameItem,
                                                                        List<Integer> listIcon,
                                                                        List<Integer> listItensHeader,
                                                                        List<Integer> listHidden,
                                                                        int colorSelected, boolean removeSelector) {

        List<NavigationLiveoItemAdapter> mList = new ArrayList<NavigationLiveoItemAdapter>();
        if (listNameItem == null || listNameItem.size() == 0) {
            throw new RuntimeException("List of null or empty names. Solution: mListNameItem = new ArrayList <> (); mListNameItem.add (position, R.string.name);");
        }

        int icon;
        boolean isHeader;
        boolean isHidden;

        for (int i = 0; i < listNameItem.size(); i++) {

            String title = listNameItem.get(i);

            NavigationLiveoItemAdapter mItemAdapter;

            icon = (listIcon != null ? listIcon.get(i) : 0);
            isHeader = (listItensHeader != null && listItensHeader.contains(i));
            isHidden = (listHidden != null && listHidden.contains(i));

            if (isHeader && icon > 0){
                throw new RuntimeException("The value of the icon for a subHeader item should be 0");
            }

            if (!isHeader) {
                if (title == null) {
                    throw new RuntimeException("Enter the item name position " + i);
                }

                if (title.trim().equals("")) {
                    throw new RuntimeException("Enter the item name position " + i);
                }
            }

            mItemAdapter = new NavigationLiveoItemAdapter(title, icon, isHeader, isHidden, colorSelected, removeSelector);
            mList.add(mItemAdapter);
        }
        return mList;
    }

}
