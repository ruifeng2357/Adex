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

public class NavigationLiveoItemAdapter {

	public String title;
	public int icon;
	public boolean isHeader;
	public boolean isHidden;
    public int colorSelected = 0;
	public boolean checked = false;
    public boolean removeSelector = false;

	public NavigationLiveoItemAdapter(String title, int icon, boolean header, boolean hidden, int colorSelected, boolean removeSelector) {
		this.title = title;
		this.icon = icon;
		this.isHeader = header;
		this.isHidden = hidden;
        this.colorSelected = colorSelected;
        this.removeSelector = removeSelector;
	}
}
