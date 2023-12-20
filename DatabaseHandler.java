package com.example.sudokuapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SudDb";
    private static final int DATABASE_VERSION = 1;
    private static final String SUDOKU_TABLE = "tb_sudoku";
    private static final String SUDOKU_WON = "tb_won";

    private static final String HISTORY_SUDOKU = "history_sudoku";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_GRID_DATA = "grid_data";
    private static final String ORIGINAL_VALUES = "original_values";
    private static final String LEVELS = "level_emh";
    private static final String TIME  ="time";
    private static final String STATUS ="status";

    public  String level,time;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to store Sudoku grid values
        String createTableQuery = "CREATE TABLE " + SUDOKU_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GRID_DATA + " TEXT, " +
                ORIGINAL_VALUES + " TEXT," +
                LEVELS + " TEXT, "+
                TIME + " TEXT, "+
                STATUS + " TEXT) ";

        String createWonTable = "CREATE TABLE " + SUDOKU_WON + " (" +
                LEVELS + " TEXT, "+
                TIME + " TEXT, "+
                STATUS + " TEXT) ";

        db.execSQL(createTableQuery);
        db.execSQL(createWonTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SUDOKU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUDOKU_WON);
        onCreate(db);
    }

    void addGrid(GridValue gValue) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_GRID_DATA, String.valueOf(gValue.setDummyData()));
//        values.put(ORIGINAL_VALUES, String.valueOf(gValue.setDummyOriginal()));
//        values.put(LEVELS, gValue.setLevel());
//        values.put(TIME,gValue.setTime());
//        values.put(STATUS,gValue.setStatus());
//
//        database.insert(SUDOKU_TABLE, null, values);
//        database.close();

        SQLiteDatabase database = this.getWritableDatabase();

        // Check if the last record in the table has a status of "save"
        String query = "SELECT * FROM " + SUDOKU_TABLE + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String lastRecordStatus = cursor.getString(cursor.getColumnIndex(STATUS));

            if ("save".equals(lastRecordStatus)) {
                // If the last record has a status of "save," update it
                @SuppressLint("Range") long lastRecordId = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                ContentValues values = new ContentValues();
                values.put(COLUMN_GRID_DATA, String.valueOf(gValue.setDummyData()));
                values.put(TIME, gValue.setTime());
                values.put(STATUS, gValue.setStatus());
                database.update(SUDOKU_TABLE, values, COLUMN_ID + " = ?", new String[] { String.valueOf(lastRecordId) });
            }
            cursor.close();
        } else {
            // Insert the current record
            ContentValues values = new ContentValues();
            values.put(COLUMN_GRID_DATA, String.valueOf(gValue.setDummyData()));
            values.put(ORIGINAL_VALUES, String.valueOf(gValue.setDummyOriginal()));
            values.put(LEVELS, gValue.setLevel());
            values.put(TIME, gValue.setTime());
            values.put(STATUS, gValue.setStatus());
            database.insert(SUDOKU_TABLE, null, values);
        }
        database.close();
    }

    public void addWonGrid(GridValue gridValue){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEVELS, gridValue.setLevel());
        values.put(TIME,gridValue.setTime());
        values.put(STATUS, gridValue.setStatus());
        database.insert(SUDOKU_WON, null, values);

        database.close();


    }



    public GridValue getLastSavedGrid() {
//        SQLiteDatabase database = this.getReadableDatabase();
//        String[] columns = {COLUMN_GRID_DATA, ORIGINAL_VALUES, LEVELS};
//        Cursor cursor = database.query(SUDOKU_TABLE, columns, null, null, null, null, COLUMN_ID + " DESC", "1");
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                int gridDataColumnIndex = cursor.getColumnIndex(COLUMN_GRID_DATA);
//                int originalDataColumnIndex = cursor.getColumnIndex(ORIGINAL_VALUES);
//                int levelColumnIndex = cursor.getColumnIndex(LEVELS);
//                int timeColumnIndex = cursor.getColumnIndex(TIME);
//                int statusColumnIndex = cursor.getColumnIndex(STATUS);
//
//                if (gridDataColumnIndex != -1 && originalDataColumnIndex != -1 && levelColumnIndex != -1) {
//                    String gridData = cursor.getString(gridDataColumnIndex);
//                    String originalData = cursor.getString(originalDataColumnIndex);
//                    level = cursor.getString(levelColumnIndex);
//                    time = cursor.getString(timeColumnIndex);
//                    String status=cursor.getString(statusColumnIndex);
//
//                    cursor.close();
//                    return new GridValue(new StringBuilder(gridData), new StringBuilder(originalData), level,time,status);
//                }
//            }
//            cursor.close();
//        }
//        return null;

        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = {COLUMN_GRID_DATA, ORIGINAL_VALUES, LEVELS,TIME,STATUS};
        Cursor cursor = database.query(SUDOKU_TABLE, columns, null, null, null, null, COLUMN_ID + " DESC", "1");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int gridDataColumnIndex = cursor.getColumnIndex(COLUMN_GRID_DATA);
                int originalDataColumnIndex = cursor.getColumnIndex(ORIGINAL_VALUES);
                int levelColumnIndex = cursor.getColumnIndex(LEVELS);
                int timeColumnIndex = cursor.getColumnIndex(TIME);
                int statusColumnIndex = cursor.getColumnIndex(STATUS);

                if (gridDataColumnIndex != -1 && originalDataColumnIndex != -1 && levelColumnIndex != -1 && timeColumnIndex!=-1) {
                    String gridData = cursor.getString(gridDataColumnIndex);
                    String originalData = cursor.getString(originalDataColumnIndex);
                    level = cursor.getString(levelColumnIndex);
                    time = cursor.getString(timeColumnIndex);
                    String status=cursor.getString(statusColumnIndex);
                    cursor.close();

                    System.out.println("DATABASE");
                    System.out.println(level);
                    System.out.println(time);
                    System.out.println(status);
                    return new GridValue(new StringBuilder(gridData), new StringBuilder(originalData), level,time,status);
                }
                else{
                    System.out.println("NOT SUCCESSFULL");
                }
            }
            cursor.close();
        }
        return null;
    }

    public int getRowCount(String levels){
        SQLiteDatabase database = this.getReadableDatabase();
        String tableName = SUDOKU_WON;
        String selection = "level_emh" + " = ?";
        String []level={levels};
        long count = DatabaseUtils.queryNumEntries(database, tableName, selection, level);
        // Close the database connection
        database.close();
        // Return the row count as an integer
        return (int) count;
    }

    public int getWonCount(String levels){
        SQLiteDatabase database = this.getReadableDatabase();
        String tableName = SUDOKU_WON;
        String selection = "level_emh = ? AND STATUS = ?";
        String[] selectionArgs = {levels, "won"};
        long count = DatabaseUtils.queryNumEntries(database, tableName, selection, selectionArgs);
        // Close the database connection
        database.close();
        // Return the row count as an integer
        return (int) count;
    }

    public void deleteRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = SUDOKU_TABLE;
        db.delete(tableName, null, null);
        db.close();
    }

    public boolean checkEmpty() {
        SQLiteDatabase db =this.getReadableDatabase();
        String tableName = SUDOKU_TABLE;
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        if (cursor.getCount() > 0) {
            return false;
        }
        else
            return true;
    }


    public String  getBestTime(String level){

    SQLiteDatabase database = this.getReadableDatabase();
        String highestTimeValue = null;

        String tableName = SUDOKU_WON;
//        String timeColumn = TIME;
//
//        String query = "SELECT MAX(" + TIME + ") FROM " + tableName;
//
//        Cursor cursor = database.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            highestTimeValue = cursor.getString(0);
//        }
//
//        cursor.close();
//        database.close();
//        return highestTimeValue;

        // Modify the query to select the maximum time value for the specified level
        String query = "SELECT MAX(" + TIME + ") FROM " + tableName + " WHERE " + LEVELS + " = ? AND " + STATUS + " = ?";
        String[] selectionArgs = { level, "won" };

        Cursor cursor = database.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            highestTimeValue = cursor.getString(0);
        }
        cursor.close();
        database.close();
        return highestTimeValue;

}

    public String getAverageTime(String level) {
        SQLiteDatabase database = this.getReadableDatabase();
        String tableName = SUDOKU_WON;
        // Create the query to select all time values for the specified level and status
        String query = "SELECT " + TIME + " FROM " + tableName + " WHERE " + LEVELS + " = ? AND " + STATUS + " = ?";
        String[] selectionArgs = { level, "won" };

        Cursor cursor = database.rawQuery(query, selectionArgs);

        long totalTimeInSeconds = 0;
        int count = 0;

        if (cursor.moveToFirst()) {
            do {
                String timeValue = cursor.getString(0);

                // Subtract each time value from "10:00"
                String[] parts = timeValue.split(":");
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);

                // Convert the time value to seconds and subtract it from 10 minutes
                long timeInSeconds = (10 * 60) - (minutes * 60 + seconds);

                // Add the adjusted time value to the total time
                totalTimeInSeconds += timeInSeconds;
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        // Calculate the average time in seconds
        long averageTimeInSeconds = (count > 0) ? totalTimeInSeconds / count : 0;

        // Convert the average time back to the "mm:ss" format
        long minutes = averageTimeInSeconds / 60;
        long seconds = averageTimeInSeconds % 60;
        String averageTimeFormatted = String.format("%02d:%02d", minutes, seconds);

        return averageTimeFormatted;

    }

}
