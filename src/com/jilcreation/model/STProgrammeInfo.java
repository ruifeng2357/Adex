package com.jilcreation.model;

import org.json.JSONObject;

/**
 * Created by ruifeng on 2015/3/16.
 */
public class STProgrammeInfo {
    public long scheduleId;
	public String dayName;
	public int dayNumber;
	public String dateFormat;
	public String speackerName;
	public String desc;
	public String startTime;
	public String endTime;

    public STProgrammeInfo() {
		scheduleId = 0;
		dayName = "";
		dayNumber = 0;
		dateFormat = "";
		speackerName = "";
		desc = "";
		startTime = "";
		endTime = "";
	}

    public static STProgrammeInfo decodeFromJSON(JSONObject jsonObj)
    {
		STProgrammeInfo retData = new STProgrammeInfo();

        try { retData.scheduleId = jsonObj.getLong("schedule_id"); } catch (Exception ex) {}
        try { retData.dayName = jsonObj.getString("day_name"); if (retData.dayName == null) retData.dayName = ""; } catch (Exception ex) {}
		try { retData.dayNumber = jsonObj.getInt("day_number");} catch (Exception ex) {}
		try { retData.dateFormat = jsonObj.getString("date_format(`date`,'%e %M %Y')"); if (retData.dateFormat == null) retData.dateFormat = ""; } catch (Exception ex) {}
		try { retData.speackerName = jsonObj.getString("speaker_name"); if (retData.speackerName == null) retData.speackerName = ""; } catch (Exception ex) {}
		try { retData.desc = jsonObj.getString("description"); if (retData.desc == null) retData.desc = ""; } catch (Exception ex) {}
		try { retData.startTime = jsonObj.getString("time_format(start_time,'%H:%i' )"); if (retData.startTime == null) retData.startTime = ""; } catch (Exception ex) {}
		try { retData.endTime = jsonObj.getString("time_format(end_time,'%H:%i' )"); if (retData.endTime == null) retData.endTime = ""; } catch (Exception ex) {}

        return retData;
    }
}
