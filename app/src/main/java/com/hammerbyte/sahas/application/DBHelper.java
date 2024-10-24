package com.hammerbyte.sahas.application;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hammerbyte.sahas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, context.getString(R.string.APP_NAME), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_usage(user_email TEXT,activity_name TEXT,date TEXT DEFAULT (date('now', 'localtime')),time TEXT DEFAULT (time('now', 'localtime')))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_usage");
        onCreate(db);
    }

    public void insertUsageData(HashMap<String, String> usageData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Map.Entry<String, String> entry : usageData.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }
        db.insert("tb_usage", null, values);
        db.close();
    }

    @SuppressLint("Range")
    public JSONObject getAllUsageData() {
        JSONObject usageData = null;
        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("SELECT * FROM tb_usage", null)) {
            if (cursor.moveToFirst()) {
                usageData = new JSONObject();
                JSONArray usageDataArray = new JSONArray();
                do {
                    JSONObject usageDataRow = new JSONObject();
                    // Assuming your table has columns like user_email, activity_name, date, and time
                    usageDataRow.put("user_email", cursor.getString(cursor.getColumnIndex("user_email")));
                    usageDataRow.put("activity_name", cursor.getString(cursor.getColumnIndex("activity_name")));
                    usageDataRow.put("date", cursor.getString(cursor.getColumnIndex("date")));
                    usageDataRow.put("time", cursor.getString(cursor.getColumnIndex("time")));
                    // Add the JSON object to the array
                    usageDataArray.put(usageDataRow);
                } while (cursor.moveToNext());
                usageData.put("usage_data", usageDataArray);
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Log the error or handle it as needed
        }
        return usageData;
    }

    public void truncateUsageData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tb_usage");
        db.close();
    }
}
