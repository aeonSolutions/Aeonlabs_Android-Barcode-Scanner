package aeonlabs.barcodescanner.common.libraries.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import aeonlabs.barcodescanner.common.libraries.model.BarCode;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "barcodes";
    private static final String TABLE_BARCODES = "barcodes";


    private static final String CREATE_TABLE_INSTRUCTOR = "create table if not exists "
            + TABLE_BARCODES
            + " (id integer primary key autoincrement,"
            + " bar_code varchar(30)," + " scan_time varchar(30), " + " scan_date varchar(30),"
            + " latitude varchar(30),"+ " longitude varchar(30),"+ " sent varchar(1),"+" archived varchar(1));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_INSTRUCTOR);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODES);

        onCreate(db);
    }

    public void BarCodeSQLquery(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void addBarCode(BarCode barCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("bar_code", barCode.getBarcodeNumber());
        values.put("scan_time", barCode.getScanTime());
        values.put("scan_date", barCode.getScanDate());
        values.put("longitude", barCode.getLongitude());
        values.put("latitude", barCode.getLatitude());
        values.put("sent", "0");
        values.put("archived", "0");

        db.insert(TABLE_BARCODES, null, values);
        db.close();

    }


    public ArrayList<Object> getAllBarCodes() {
        ArrayList<Object> barCodeArrayList = new ArrayList<Object>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BARCODES+" ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BarCode barcode = new BarCode();
                barcode.setBarcodeId(cursor.getString(0));
                barcode.setBarcodeNumber(cursor.getString(1));
                barcode.setScanTime(cursor.getString(2));
                barcode.setScanDate(cursor.getString(3));
                barcode.setLatitude(cursor.getString(4));
                barcode.setLongitude(cursor.getString(5));
                barcode.setSent(cursor.getString(6));
                barcode.setArchived(cursor.getString(7));
                // Adding contact to list
                barCodeArrayList.add(barcode);
            } while (cursor.moveToNext());
        }

        // return contact list
        return barCodeArrayList;
    }
}

