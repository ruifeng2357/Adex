package com.jilcreation.model.modelmanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jilcreation.model.STHisFavInfo;

import java.util.ArrayList;

/*
 * Created by Ruifeng
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {
    /*
    * Database Name Definition
    */
    private final static String DATABASE_NAME = "Adex";
    private final static int DATABASE_VERSION = 1;

    /*
    * Table Name Definition
    */
    private final static String TABLE_NAME = "tbl_product";
    /*
    * Table Fields Definition
    */
    public final static String FIELD_ID = "uid";
    public final static String FIELD_DEALID = "dealId";
    public final static String FIELD_MERCHANTID = "merchantId";
    public final static String FIELD_MERCHANTNAME = "merchantName";
    public final static String FIELD_BOOTH = "booth";
    public final static String FIELD_BRAND = "brand";
    public final static String FIELD_PRODUCTNAME = "productName";
    public final static String FIELD_IMGURL = "productImg";
    public final static String FIELD_ISTAPPED = "isTapped";
    public final static String FIELD_ISFAVORITED = "isFavorited";
    public final static String FIELD_LOCIMAGEURL = "locImgPath";

    /*
    * Table Name Definition
    */
    private final static String TABLE_NOTIFICATIONNAME = "tbl_notification";
    /*
    * Table Fields Definition
    */
    public final static String FIELD_NOTIFICATION_ID = "uid";
    public final static String FIELD_NOTIFICATION_BEACONID = "dealId";
    public final static String FIELD_NOTIFICATION_DAY = "merchantId";

    public SQLiteDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="Create table " + TABLE_NAME + "( " + FIELD_ID + " integer primary key autoincrement,"
                + FIELD_DEALID + " integer, "
                + FIELD_MERCHANTID + " integer, "
                + FIELD_MERCHANTNAME + " text, "
                + FIELD_BOOTH + " text, "
                + FIELD_BRAND + " text, "
                + FIELD_PRODUCTNAME + " text, "
                + FIELD_IMGURL + " text, "
                + FIELD_ISTAPPED + " integer, "
                + FIELD_ISFAVORITED + " integer, "
                + FIELD_LOCIMAGEURL + " text"
                + ");";
        db.execSQL(sql);
        sql="Create table " + TABLE_NOTIFICATIONNAME + "( " + FIELD_NOTIFICATION_ID + " integer primary key autoincrement,"
                + FIELD_NOTIFICATION_BEACONID + " integer, "
                + FIELD_NOTIFICATION_DAY + " integer"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sqlQuery = "DROP TABLE IF EXISTS " + DATABASE_NAME;
        db.execSQL(sqlQuery);
        onCreate(db);
    }
//
//    public Cursor select( long dealId )
//    {
//        Cursor cursor = null;
//
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            cursor = db.query(TABLE_NAME,
//                new String[] { FIELD_APKID, FIELD_PATH, FIELD_SIZE },
//                FIELD_APKID + "=?",
//                new String[]{String.valueOf(nAPKID)},
//                null,
//                null,
//                null,
//                null);
//
//            if (cursor != null)
//                cursor.moveToFirst();
//        } catch (Exception ex) {
//            cursor = null;
//        }
//
//        return cursor;
//    }
//
//    public Cursor selectAll()
//    {
//        Cursor cursor = null;
//
//        try {
//            SQLiteDatabase db = this.getReadableDatabase();
//            cursor = db.query(TABLE_NAME,
//                    new String[] { FIELD_APKID, FIELD_PATH, FIELD_VERSION, FIELD_PACKAGE, FIELD_SIZE },
//                    null,
//                    null,
//                    null,
//                    null,
//                    null,
//                    null);
//
//            if (cursor != null)
//                cursor.moveToFirst();
//        } catch (Exception ex) {
//            cursor = null;
//        }
//
//        return cursor;
//    }

    public long getExistProduct(long dealId)
    {
        long nUID = -1;
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_ID },
                    FIELD_DEALID + "=?",
                    new String[]{String.valueOf(dealId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            nUID = Long.parseLong(cursor.getString(0));

        } catch (Exception ex) {
            nUID = -1;
        }

        return nUID;
    }

    public int getIsTapped(long dealId)
    {
        int nIsTapped = 0;
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_ISTAPPED },
                    FIELD_DEALID + "=?",
                    new String[]{String.valueOf(dealId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            nIsTapped = Integer.parseInt(cursor.getString(0));

        } catch (Exception ex) {
            nIsTapped = 0;
        }

        return nIsTapped;
    }

    public long insert(long dealId, long merchantId, String merchantName, String booth, String brandName, String productName, String imgUrl, int isTapped, int isFavorited)
    {
        if (getExistProduct(dealId) > 0)
            return -1;

        long row = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FIELD_DEALID, dealId);
            cv.put(FIELD_MERCHANTID, merchantId);
            cv.put(FIELD_MERCHANTNAME, merchantName);
            cv.put(FIELD_BOOTH, booth);
            cv.put(FIELD_BRAND, brandName);
            cv.put(FIELD_PRODUCTNAME, productName);
            cv.put(FIELD_IMGURL, imgUrl);
            cv.put(FIELD_ISTAPPED, isTapped);
            cv.put(FIELD_ISFAVORITED, isFavorited);
            cv.put(FIELD_LOCIMAGEURL, "");
            row = db.insert(TABLE_NAME, null, cv);
        } catch (Exception ex) {
            row = 0;
        }

        return row;
    }

    public boolean delete(long dealId)
    {
        boolean bRet = true;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String where = FIELD_DEALID + "=?";
            String[] whereValue = {Long.toString(dealId)};
            db.delete(TABLE_NAME, where, whereValue);
        } catch (Exception ex) {
            bRet = false;
        }

        return bRet;
    }

    public boolean deleteAll()
    {
        boolean bRet = true;
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            String where="1=?";
            String[] whereValue={Long.toString(1)};
            db.delete(TABLE_NAME, where, whereValue);
        } catch (Exception ex) {
            bRet = false;
        }

        return bRet;
    }

    public boolean setTapped(long dealId, int isTapped)
    {
        boolean bRet = true;

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            String where = FIELD_DEALID + "=?";
            String[] whereValue={Long.toString(dealId)};
            ContentValues cv=new ContentValues();
            cv.put(FIELD_ISTAPPED, isTapped);
            db.update(TABLE_NAME, cv, where, whereValue);
        } catch(Exception ex) {
            bRet = false;
        }

        return bRet;
    }

    public boolean setFavorited(long dealId, int isFavorited)
    {
        boolean bRet = true;

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            String where = FIELD_DEALID + "=?";
            String[] whereValue={Long.toString(dealId)};
            ContentValues cv=new ContentValues();
            cv.put(FIELD_ISFAVORITED, isFavorited);
            db.update(TABLE_NAME, cv, where, whereValue);
        } catch(Exception ex) {
            bRet = false;
        }

        return bRet;
    }

    public int getIsFavorite(long dealId)
    {
        int nIsFavorited = 0;
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_ISFAVORITED },
                    FIELD_DEALID + "=?",
                    new String[]{String.valueOf(dealId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            nIsFavorited = Integer.parseInt(cursor.getString(0));

        } catch (Exception ex) {
            nIsFavorited = 0;
        }

        return nIsFavorited;
    }

    public ArrayList<STHisFavInfo> getHistoryList()
    {
        Cursor cursor = null;
        ArrayList<STHisFavInfo> arrayList = new ArrayList<STHisFavInfo>();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_DEALID, FIELD_MERCHANTID, FIELD_BRAND, FIELD_PRODUCTNAME, FIELD_IMGURL },
                    FIELD_ISTAPPED + "=1",
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

        } catch (Exception ex) {
            arrayList = new ArrayList<STHisFavInfo>();
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    STHisFavInfo stHisFavInfo = new STHisFavInfo();
                    stHisFavInfo.dealId = cursor.getLong(0);
                    stHisFavInfo.merchantId = cursor.getLong(1);
                    stHisFavInfo.brandName = cursor.getString(2);
                    stHisFavInfo.productName = cursor.getString(3);
                    stHisFavInfo.imgUrl = cursor.getString(4);

                    arrayList.add(stHisFavInfo);
                } while (cursor.moveToNext());
            }
        }
        db.close();

        return arrayList;
    }

    public ArrayList<STHisFavInfo> getFavouriteList()
    {
        Cursor cursor = null;
        ArrayList<STHisFavInfo> arrayList = new ArrayList<STHisFavInfo>();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_DEALID, FIELD_MERCHANTID, FIELD_BRAND, FIELD_PRODUCTNAME, FIELD_IMGURL },
                    FIELD_ISFAVORITED + "=1",
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

        } catch (Exception ex) {
            arrayList = new ArrayList<STHisFavInfo>();
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    STHisFavInfo stHisFavInfo = new STHisFavInfo();
                    stHisFavInfo.dealId = cursor.getLong(0);
                    stHisFavInfo.merchantId = cursor.getLong(1);
                    stHisFavInfo.brandName = cursor.getString(2);
                    stHisFavInfo.productName = cursor.getString(3);
                    stHisFavInfo.imgUrl = cursor.getString(4);

                    arrayList.add(stHisFavInfo);
                } while (cursor.moveToNext());
            }
        }
        db.close();

        return arrayList;
    }

    public boolean setLocImgPath(long dealId, String locImgPath)
    {
        boolean bRet = true;

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            String where = FIELD_DEALID + "=?";
            String[] whereValue={Long.toString(dealId)};
            ContentValues cv=new ContentValues();
            cv.put(FIELD_LOCIMAGEURL, locImgPath);
            db.update(TABLE_NAME, cv, where, whereValue);
        } catch(Exception ex) {
            bRet = false;
        }

        return bRet;
    }

    public String getLocImgPath(long dealId)
    {
        String locImgPath = "";
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[] { FIELD_LOCIMAGEURL },
                    FIELD_DEALID + "=?",
                    new String[]{String.valueOf(dealId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            locImgPath = cursor.getString(0);

        } catch (Exception ex) {
            locImgPath = "";
        }

        return locImgPath;
    }

    public long insertNotification(int beaconId, int days)
    {
        long row = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FIELD_NOTIFICATION_BEACONID, beaconId);
            cv.put(FIELD_NOTIFICATION_DAY, days);
            row = db.insert(TABLE_NOTIFICATIONNAME, null, cv);
        } catch (Exception ex) {
            row = 0;
        }

        return row;
    }

    public long getNotification(int beaconId, int days)
    {
        long notificationId = 0;
        Cursor cursor = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(TABLE_NOTIFICATIONNAME,
                    new String[] { FIELD_NOTIFICATION_ID },
                    FIELD_NOTIFICATION_BEACONID + "=? and " + FIELD_NOTIFICATION_DAY + "=?",
                    new String[]{String.valueOf(beaconId), String.valueOf(days)},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null)
                cursor.moveToFirst();

            notificationId = cursor.getLong(0);

        } catch (Exception ex) {
            notificationId = 0;
        }

        return notificationId;
    }
}
