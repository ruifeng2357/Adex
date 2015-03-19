package com.jilcreation.model.modelmanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public final static String FIELD_ISTAPPED = "isTapped";
    public final static String FIELD_ISFAVORITED = "isFavorited";

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
                + FIELD_ISTAPPED + " integer, "
                + FIELD_ISFAVORITED + " integer "
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

    public long insert(long dealId, long merchantId, int isTapped, int isFavorited)
    {
        if (getExistProduct(dealId) > 0)
            return -1;

        long row = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(FIELD_DEALID, dealId);
            cv.put(FIELD_MERCHANTID, merchantId);
            cv.put(FIELD_ISTAPPED, isTapped);
            cv.put(FIELD_ISFAVORITED, isFavorited);
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
}
