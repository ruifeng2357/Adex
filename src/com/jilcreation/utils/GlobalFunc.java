package com.jilcreation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalFunc
{
	public static int ANIM_COVER_FROM_LEFT() { return 0; }
	public static int ANIM_COVER_FROM_RIGHT() { return 1; }

 	public static int ANIM_NONE() { return -1; }
	public static String ANIM_DIRECTION() { return "anim_direction"; }

	public static String getPreferenceName() { return "gomock"; }

	public static Toast gToast = null;

	public static Context gContext = null;
	public static void showTextToast(Activity activity, String toastStr)
	{
		if (gToast == null || gToast.getView().getWindowVisibility() != View.VISIBLE)
		{
			gToast = Toast.makeText(activity, toastStr, Toast.LENGTH_SHORT);
			gToast.show();
		}
	}

	public static boolean isExternalStorageRemovable()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			return Environment.isExternalStorageRemovable();

		return true;
	}

	public static File getExternalCacheDir(Context context) {
		File retFile = null;
		if (hasExternalCacheDir()) {
			retFile = context.getExternalCacheDir();
		}

		if (retFile == null)
		{
			final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
			retFile = new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
		}

		return retFile;
	}

	public static boolean hasExternalCacheDir()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static void logMessage(String szMsg)
	{
		Log.d("USN", szMsg);
	}

	public static String eatSpaces(String szText)
	{
		return szText.replaceAll(" ", "");
	}

	public static String eatFullStops(String szText)
	{
		return szText.replaceAll(".", "");
	}

	public static String eatChinesePunctuations(String szText)
	{
		String szResult = szText;
		String szPuncs = "。？！，、；：“”‘’（）-·《》〈〉";
		String szPuncs2 = "——";
		String szPuncs3 = "……";

		szResult = szResult.replaceAll(szPuncs2, "");
		szResult = szResult.replaceAll(szPuncs3, "");
		for (int i = 0; i < szPuncs.length(); i++)
		{
			char chrItem = szPuncs.charAt(i);
			String szItem = "" + chrItem;
			szResult = szResult.replaceAll(szItem, "");
		}

		return szResult;
	}

	public static Point getScreenSize(Context appContext)
	{
		Point ptSize = new Point(0, 0);

		WindowManager wm = (WindowManager)appContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		if (getSystemVersion(appContext) >= Build.VERSION_CODES.HONEYCOMB_MR2)
			display.getSize(ptSize);
		else
			ptSize = new Point(display.getWidth(), display.getHeight());

		return ptSize;
	}

	public static String getCurDateTime(Calendar calendar, boolean containTime)
	{
		int nYear = calendar.get(Calendar.YEAR);
		int nMonth = calendar.get(Calendar.MONTH) + 1;
		int nDay = calendar.get(Calendar.DAY_OF_MONTH);

		String szDate = String.format("%d-%02d-%02d", nYear, nMonth, nDay);

		if (!containTime)
			return szDate;

		int nHour = calendar.get(Calendar.HOUR_OF_DAY);
		int nMinute = calendar.get(Calendar.MINUTE);
		int nSecond = calendar.get(Calendar.SECOND);

		String szTime = String.format("%02d-%02d-%02d", nHour, nMinute, nSecond);

		return szDate + " " + szTime;
	}

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

	public static void callPhone(String szPhoneNum, Context context)
	{
		try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + szPhoneNum));
            context.startActivity(intent);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendSMSWithDefaultApp(String szText, Context context)
	{
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("sms:"));
		smsIntent.putExtra("sms_body", szText == null ? "" : szText);
		context.startActivity(smsIntent);
	}

	public static int getSystemVersion(Context appContext)
	{
		int nVersion = 0;

		try
		{
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			nVersion = pInfo.versionCode;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			nVersion = -1;
		}

		return nVersion;
	}

	public static String getSelfAppVersionName(Context appContext)
	{
		String szPack = "";

		try
		{
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			szPack = pInfo.versionName;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			szPack = "";
		}

		return szPack;
	}

	public static int getSelfAppVersionCode(Context appContext)
	{
		int verCode = 0;

		try
		{
			PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
			verCode = pInfo.versionCode;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			verCode = 0;
		}

		return verCode;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels)
	{
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static String encodeWithBase64(Bitmap bitmap)
	{
		if (bitmap == null)
			return "";

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return Base64.encodeToString(byteArray, Base64.NO_WRAP);
	}

	public static void showKeyboardFromText(EditText editText, Context context)
	{
		InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	public static void hideKeyboardFromText(EditText editText, Context context)
	{
		InputMethodManager mgr = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static void hideKeyboard(Activity activity)
	{
		View view = activity.getCurrentFocus();
		InputMethodManager mgr = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static double calcDistanceKM(double lat1, double lng1, double lat2, double lng2)
	{
		double EARTH_RADIUS_KM = 6378.137;

		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double radLng1 = Math.toRadians(lng1);
		double radLng2 = Math.toRadians(lng2);
		double deltaLat = radLat1 - radLat2;
		double deltaLng = radLng1 - radLng2;

		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLng / 2), 2)));

		distance = distance * EARTH_RADIUS_KM;

		return distance;		// unit : km
	}

	public static String convertToLetter(int iCol)
	{
		String szResult = "";
		int iAlpha;
		int iRemainder = 0;
		iAlpha = iCol / 27;
		iRemainder = iCol - (iAlpha * 26);
		if (iAlpha > 0)
			szResult = Character.toString((char)(iAlpha + 64));

		if (iRemainder > 0)
			szResult += Character.toString((char)(iRemainder + 64));

		return szResult;
	}

	public static int getRelativeLeft(View myView) {
		if (myView.getParent() == null)
			return 0;

		if (myView.getParent() == myView.getRootView())
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

	public static int getRelativeTop(View myView) {
		if (myView.getParent() == null)
			return 0;

		if (myView.getParent() == myView.getRootView())
			return myView.getTop();
		else
			return myView.getTop() + getRelativeTop((View) myView.getParent());
	}

	public static Rect getGlobalRect(View v)
	{
		int nLeft = getRelativeLeft(v);
		int nTop = getRelativeTop(v);
		return new Rect(nLeft, nTop, nLeft + v.getWidth(), nTop + v.getHeight());
	}

	public static int statusBarHeight(Activity activity) {
		Rect rectgle= new Rect();
		Window window= activity.getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		int statusBar = rectgle.top;
		return statusBar;
	}

	public static boolean isValidEmail(String email)
	{
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
			isValid = true;

		return isValid;
	}

	public static int getApiVersion()
	{
		return Build.VERSION.SDK_INT;
	}

	public static String getIMEI(Context context)
	{
		TelephonyManager tm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static boolean IsAppInstalled(Context appContext, String packname)
	{
		return getOtherAppVersionName(appContext, packname) != null;
	}

	public static String getOtherAppVersionName(Context appContext, String packname)
	{
		PackageManager pm = appContext.getPackageManager();
		String versionName = null;

		try
		{
			PackageInfo packageInfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
			versionName = packageInfo.versionName;
		}
		catch (PackageManager.NameNotFoundException ex)
		{
			versionName = null;
		}

		return versionName;
	}

	public static void startOtherApp(Context context, String pack_name)
	{
		PackageManager pm = context.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(pack_name);
		context.startActivity(intent);
	}

	public static Bitmap getCroppedRoundBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawOval(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

    public static String encodeToUTF8(String str)
    {
        String tmp;

        try {
            tmp = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            tmp = str;
        }

        return tmp;
    }

	public static String date2String(Date dtvalue, boolean withTime)
	{
		String szResult = "";
		if (dtvalue == null)
			return "";

		DateFormat df = null;
		if (withTime)
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else
			df = new SimpleDateFormat("yyyy-MM-dd");
		szResult = df.format(dtvalue);

		return szResult;
	}

	public static Date string2Date(String szTime)
	{
		if (szTime == null || szTime.equals("") || szTime.contains("0000-00-00"))
			return null;

		DateFormat df = null;
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dtValue = null;
		try
		{
			dtValue = df.parse(szTime);
		}
		catch (Exception ex)
		{
            try
            {
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dtValue = df.parse(szTime);
            }
            catch (Exception ex1)
            {
                df = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                    dtValue = df.parse(szTime);
                }
                catch (Exception ex2)
                {
                    ex.printStackTrace();
                }
            }
		}

		return dtValue;
	}

	public static Bitmap rotateImage(String pathToImage, int nAngle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(nAngle);

		Bitmap sourceBitmap = BitmapFactory.decodeFile(pathToImage);
		return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
	}

	public static int getImageOrientation(String imagePath){
		int nAngle = 0;
		try {
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(
					imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					nAngle = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					nAngle = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					nAngle = 90;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nAngle;
	}

	public static String removeSpecialCharacter(String strVal)
	{
		String strRet = "";
		strRet = strVal.replaceAll("[^a-zA-Z_0-9]+","");

		return strRet;
	}
}
