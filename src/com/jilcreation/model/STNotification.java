package com.jilcreation.model;

import org.json.JSONObject;

/**
 * Created by ruifeng on 2015/3/16.
 */
public class STNotification {
    public long beaconId;
	public long merchantId;
	public String message;

    public STNotification() {
		beaconId = 0;
		merchantId = 0;
		message = "";
	}

	public static STNotification decodeFromJSON(JSONObject jsonObj) {
		STNotification notification = new STNotification();

		try { notification.beaconId = jsonObj.getLong("beacon_id"); } catch (Exception ex) { notification.beaconId = 0; }
		try { notification.merchantId = jsonObj.getLong("merchant_id"); } catch (Exception ex) { notification.merchantId = 0; }
		try { notification.message = jsonObj.getString("message"); } catch (Exception ex) { notification.message = ""; }

		return notification;
	}
}
