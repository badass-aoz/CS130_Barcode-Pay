package com.project.barwallet;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
 
    // Logcat tag
	private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "barcodeWallet";
 
    // Table Names
    private static final String TABLE_VOUCHER = "voucherList";
 
    // Column names
    private static final String KEY_ID = "id";
 
    private static final String KEY_NAME = "name";
    private static final String KEY_BARCODE_ID = "barcodeID";
    private static final String KEY_BARCODE_TYPE = "barcodeType";
 
 
    // Table Create Statements
    private static final String CREATE_VOUCHER_LIST = "CREATE TABLE "
            + TABLE_VOUCHER+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
            + " TEXT," + KEY_BARCODE_ID + " TEXT," +KEY_BARCODE_TYPE + " TEXT" + ")";
 
    
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_VOUCHER_LIST);
    }
    
    //return true if exists an item with the name, false otherwise
    public boolean checkName (String name){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String selectQuery = "SELECT * FROM "+ TABLE_VOUCHER +" WHERE "+KEY_NAME+" = ?";
    	Log.e(LOG,selectQuery);
    	Cursor c = db.rawQuery(selectQuery, new String[] {name});
    	
    	if(c.moveToFirst()){
    		return true;
    	}
    	else
    		return false;
    }
    
    // create a voucher item
    public long createVoucher(Voucher voucher){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	
    	values.put(KEY_NAME,voucher.getName());
    	values.put(KEY_BARCODE_ID, voucher.getBarcodeID());
    	values.put(KEY_BARCODE_TYPE, voucher.getBarcodeType());
    	
    	long voucher_id = db.insert(TABLE_VOUCHER, null, values);
    	db.close();
    	return voucher_id;
    }
    
    //remove vouchers
    public void removeVoucher(String name){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_VOUCHER, KEY_NAME+"=?", new String[]{name});
    	db.close();
    	Log.d("delete Voucher",name);
    }
    

    
    //FETCH ALL VOUCHERS
    public List<Voucher> getAllVouchers(){
    	List<Voucher> voucherList = new ArrayList<Voucher>();
    	String selectQuery = "SELECT * FROM "+ TABLE_VOUCHER;
    	Log.e(LOG,selectQuery);
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(selectQuery,null);
    	
    	if(c.moveToFirst()){
    		do{
    			Voucher voucher = new Voucher();
    			voucher.setId(c.getInt(c.getColumnIndex(KEY_ID)));
    			voucher.setName(c.getString(c.getColumnIndex(KEY_NAME)));
    			voucher.setBarcodeID(c.getString(c.getColumnIndex(KEY_BARCODE_ID)));
    			voucher.setBarcodeType(c.getString(c.getColumnIndex(KEY_BARCODE_TYPE)));
    			voucherList.add(voucher);
    		}while(c.moveToNext());
    	}
    	
    	db.close();
    	
    	return voucherList;
    	
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHER);
 
        // create new tables
        onCreate(db);
    }
}