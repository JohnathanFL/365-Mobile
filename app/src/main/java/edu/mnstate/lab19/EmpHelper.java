package edu.mnstate.lab19;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmpHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "emp.db", TABLE_NAME = "empTable", EMPID = "_id", EMPNAME = "empName", EMPADDR = "empAddress";
    public static int DB_VERSION  = 5;
    private Context context;

    public EmpHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        MainActivity.message(this.context, "Called constructor!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(255), %s TEXT);", TABLE_NAME, EMPID, EMPNAME, EMPADDR));
            MainActivity.message(this.context, "Created DB");
        } catch (SQLException e) {
            MainActivity.message(this.context, "Failed because: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            MainActivity.message(context, "Upgrading...");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch(SQLException e) {
            MainActivity.message(context, "Upgrade error: " + e);
        }
    }
}
