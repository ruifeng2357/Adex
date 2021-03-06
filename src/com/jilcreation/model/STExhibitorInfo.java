package com.jilcreation.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;

/**
 * Created by ruifeng on 2015/3/16.
 */
public class STExhibitorInfo {
    public long merchantId;
    public String merchantName;
    public String booth;

    public STExhibitorInfo() {
		merchantId = 0;
		merchantName = "";
        booth = "";
	}

    public static STExhibitorInfo decodeFromJSON(JSONObject jsonObj)
    {
        STExhibitorInfo retData = new STExhibitorInfo();

        try { retData.merchantId = jsonObj.getLong("merchant_id"); } catch (Exception ex) {}
        try { retData.merchantName = jsonObj.getString("merchant_name"); if (retData.merchantName == null) retData.merchantName = ""; } catch (Exception ex) {}
        try { retData.booth = jsonObj.getString("merchant_booth"); if (retData.booth == null) retData.booth = ""; } catch (Exception ex) {}

        return retData;
    }
}
