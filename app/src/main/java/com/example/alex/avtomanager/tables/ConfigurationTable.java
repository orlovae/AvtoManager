package com.example.alex.avtomanager.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alex on 19.03.17.
 */

public class ConfigurationTable {

    public static final String TABLE_NAME = "Configuration";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CAR_BRAND = "carBrand";
    public static final String COLUMN_CAR_MODEL = "carModel";
    public static final String COLUMN_SPEEDOMETER = "speedometer";
    public static final String COLUMN_LAST_INSPECTION = "lastInspection";
    public static final String COLUMN_INTERVAL_KM = "intervalKm";
    public static final String COLUMN_INTERVAL_MOUNT = "intervalMount";
    public static final String COLUMN_REMINDER_KM = "reminderKm";
    public static final String COLUMN_DATE_RECALL = "dateRecall";
    public static final String COLUMN_MEMO = "memo";

    public static void createTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " integer primary key autoincrement,"
                + COLUMN_CAR_BRAND + " text,"
                + COLUMN_CAR_MODEL + " text,"
                + COLUMN_SPEEDOMETER + " integer,"
                + COLUMN_LAST_INSPECTION + " integer,"
                + COLUMN_INTERVAL_KM + " integer,"
                + COLUMN_INTERVAL_MOUNT + " integer,"
                + COLUMN_REMINDER_KM + " integer,"
                + COLUMN_DATE_RECALL + " integer,"
                + COLUMN_MEMO + " text);");
    }
}
