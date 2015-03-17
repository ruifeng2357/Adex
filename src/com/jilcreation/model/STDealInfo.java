package com.jilcreation.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONObject;

/**
 * Created by ruifeng on 2015/3/16.
 */
public class STDealInfo implements Parcelable {
    public long dealId;
    public long merchantId;
    public String productBrand;
    public String productName;
    public double price;
    public String detail;
    public String imageUrl;
    public String imageUrl2;
    public String imageUrl3;
    public String category;
    public int totalCount;
    public String lastUpdateBy;
    public String lastUpdateTime;

    public STDealInfo () {}

    public STDealInfo (Parcel in)
    {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dealId);
        dest.writeLong(merchantId);
        dest.writeString(productBrand);
        dest.writeString(productName);
        dest.writeDouble(price);
        dest.writeString(detail);
        dest.writeString(imageUrl);
        dest.writeString(imageUrl2);
        dest.writeString(imageUrl3);
        dest.writeString(category);
        dest.writeInt(totalCount);
        dest.writeString(lastUpdateBy);
        dest.writeString(lastUpdateTime);
    }

    private void readFromParcel(Parcel in)
    {
        dealId = in.readLong();
        merchantId = in.readLong();
        productBrand = in.readString();
        productName = in.readString();
        price = in.readDouble();
        detail = in.readString();
        imageUrl = in.readString();
        imageUrl2 = in.readString();
        imageUrl3 = in.readString();
        category = in.readString();
        totalCount = in.readInt();
        lastUpdateBy = in.readString();
        lastUpdateTime = in.readString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final Parcelable.Creator<STDealInfo> CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new STDealInfo(source);
        }
        @Override
        public Object[] newArray(int size) {
            return new STDealInfo[size];
        }
    };

	public static STDealInfo decodeFromJSON(JSONObject jsonObj)
	{
		STDealInfo retData = new STDealInfo();

        try { retData.dealId = jsonObj.getLong("deal_id"); } catch (Exception ex) {}
        try { retData.merchantId = jsonObj.getLong("merchant_id"); } catch (Exception ex) {}
        try { retData.productBrand = jsonObj.getString("product_brand"); if (retData.productBrand == null) retData.productBrand = ""; } catch (Exception ex) {}
        try { retData.productName = jsonObj.getString("product_name"); if (retData.productName == null) retData.productName = ""; } catch (Exception ex) {}
        try { retData.price = jsonObj.getDouble("price"); } catch (Exception ex) {}
        try { retData.detail = jsonObj.getString("detail"); if (retData.detail == null) retData.detail = "";} catch (Exception ex) {}
        try { retData.imageUrl = jsonObj.getString("image_url"); if (retData.imageUrl == null) retData.imageUrl = "";} catch (Exception ex) {}
        try { retData.imageUrl2 = jsonObj.getString("image_url2");if (retData.imageUrl2 == null) retData.imageUrl2 = ""; } catch (Exception ex) {}
        try { retData.imageUrl3 = jsonObj.getString("image_url3"); if (retData.imageUrl3 == null) retData.imageUrl3 = "";} catch (Exception ex) {}
        try { retData.category = jsonObj.getString("category"); if (retData.category == null) retData.category = "";} catch (Exception ex) {}
        try { retData.totalCount = jsonObj.getInt("total_hits_count"); } catch (Exception ex) {}
        try { retData.lastUpdateBy = jsonObj.getString("last_update_by"); if (retData.lastUpdateBy == null) retData.lastUpdateBy = "";} catch (Exception ex) {}
        try { retData.lastUpdateTime = jsonObj.getString("last_update_time"); if (retData.lastUpdateTime == null) retData.lastUpdateTime = ""; } catch (Exception ex) {}

		return retData;
	}
}
