/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */

package edu.mnstate.jz1652ve.majorproject2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Layout of the database:
 * "Items" Table -
 *   name: TEXT (name of item) (PRIM)
 *   list: TEXT (name of list we're in) (PRIM)
 *   quant: INT
 *   price: INT
 *   category: TEXT
 *   getBy: DATE (when we need to get it by, or NULL for default day)
 *
 * For the DBMan itself, I've gone with a singleton pattern, rather than storing instances everywhere.
 */
public class DBMan extends SQLiteOpenHelper {
    public final static String DB_NAME = "grocery.db";
    public final static int DB_VER = 2;

    private static DBMan instance = null;

    private DBMan(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBMan get(Context ctx) {
        if(instance == null) {
            instance = new DBMan(ctx, DB_NAME, null, DB_VER);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Items (name TEXT, list TEXT, quant INT, price INT, category TEXT, getBy DATE, PRIMARY KEY(name, list)) ;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Items;");
    }

    public static void setItem(Context ctx, Item order) {

        SQLiteDatabase db = get(ctx).getWritableDatabase();

        Log.d("DBMan", "setItem: " + order.toString());

        db.insert("Items", null, order.toCVals());
    }

    /**
     * Get a specific item from a specific list
     * @param ctx
     * @param name
     * @param list
     * @return
     */
    public static Item getItem(Context ctx, String name, String list) {
        SQLiteDatabase db = get(ctx).getReadableDatabase();
        Cursor c = db.rawQuery("select name, list, quant, price, category, getBy from Items where (name = ? and list = ?);", new String[]{name, list});
        c.moveToFirst();

        if(c.getCount() != 0)
            return new Item(c);
        else
            return null;
    }

    // With all the context args, I feel like I should be naming these things like c99_dbman_delitem()
    public static void delItem(Context ctx, String name, String list) {
        SQLiteDatabase db = get(ctx).getWritableDatabase();
        db.delete("Items", "(name = ? and list = ?)", new String[]{name, list});
    }

    public static void delList(Context ctx, String list) {
        SQLiteDatabase db = get(ctx).getWritableDatabase();
        db.delete("Items", "list = ?", new String[]{list});
    }

    /**
     * Get all items from a specific list
     * @param ctx
     * @param list
     * @return
     */
    public static ArrayList<Item> getItemsByList(Context ctx, String list) {
        SQLiteDatabase db = get(ctx).getReadableDatabase();
        Cursor c = db.rawQuery("select name, list, quant, price, category, getBy from Items where (list = ?);", new String[]{list});

        ArrayList<Item> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(new Item(c));
        }

        return items;
    }

    /**
     * Get all product names like the input
     * @param ctx
     * @param name
     * @return
     */
    public static ArrayList<String> getItemsLikeName(Context ctx, String name) {
        SQLiteDatabase db = get(ctx).getReadableDatabase();
        Cursor c = db.rawQuery("select name from Items where (name like ?);", new String[]{"%" + name + "%"});

        ArrayList<String> items = new ArrayList<>();
        while(c.moveToNext()) {
            items.add(c.getString(0));
        }

        return items;
    }
}
