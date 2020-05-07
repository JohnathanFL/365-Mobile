package edu.mnstate.jz1652ve.finalproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.sql.SQLException

public fun relFromInt(i: Int): RelType = when(i) {
    0 -> RelType.Parent
    1 -> RelType.Child
    2 -> RelType.Sibling
    3 -> RelType.ExtFam
    4 -> RelType.Acquaintance
    5 -> RelType.Friend
    6 -> RelType.CloseFriend
    7 -> RelType.SigOth
    else -> TODO()
}

enum class RelType {
    Parent, Child, Sibling, ExtFam, Acquaintance, Friend, CloseFriend, SigOth;



    public fun toInt(): Int = when(this) {
        Parent -> 0
        Child -> 1
        Sibling -> 2
        ExtFam -> 3
        Acquaintance -> 4
        Friend -> 5
        CloseFriend -> 6
        SigOth -> 7
    }
}

data class Contact(val firstName: String, val lastName: String, val sex: Char, val married: Boolean, val rel: Int, val birthday: String, val phone: String, val lat: Double, val lng: Double, val id: Int? = null) {
    constructor(c: Cursor) : this(
        firstName = c.getString(c.getColumnIndex("firstName")),
        lastName = c.getString(c.getColumnIndex("lastName")),
        sex = c.getInt(c.getColumnIndex("sex")).toChar(),
        married = c.getInt(c.getColumnIndex("married")) == 1,
        rel = c.getInt(c.getColumnIndex("rel")),
        birthday = c.getString(c.getColumnIndex("birthday")),
        phone = c.getString(c.getColumnIndex("phone")),
        lat = c.getDouble(c.getColumnIndex("lat")),
        lng = c.getDouble(c.getColumnIndex("lng")),
        id = c.getInt(c.getColumnIndex("id"))
    ) {}

    fun toCvals(): ContentValues {
        val res = ContentValues()
        res.put("firstName", firstName)
        res.put("lastName", lastName)
        res.put("sex", sex.toInt())
        res.put("married", if (married) { 1 } else {0} )
        res.put("rel", rel)
        res.put("birthday", birthday)
        res.put("phone", phone)
        res.put("lat", lat)
        res.put("lng", lng)
        if(id != null) res.put("id", id)

        return res
    }

    override fun toString(): String {
        return "Contact(firstName='$firstName', lastName='$lastName', sex=$sex, married=$married, rel=$rel, birthday='$birthday', phone='$phone', lat=$lat, lng=$lng, id=$id)"
    }


}


class ContactHelper(val ctx: Context) : SQLiteOpenHelper(ctx, "contacts.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL("CREATE TABLE Contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, sex CHAR, married INTEGER, rel INTEGER, birthday TEXT, phone TEXT, lat REAL, lng REAL);")
            Log.d("ContactHelper", "onCreate: Created DB")
        } catch (e: Exception) {
            Log.d("ContactHelper", "onCreate: Failed to make DB because " + e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db?.execSQL("DROP TABLE IF EXISTS Contacts;")
            onCreate(db)
        } catch (e: SQLException) {
            Log.d("ContactHelper", "onCreate: Failed to upgrade DB because " + e.message)
        }
    }

    fun getAll(): List<Contact> {
        Log.d("ContactListFragment", "getAll")
        onCreate(this.writableDatabase)
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM Contacts;", arrayOf())
        val res = mutableListOf<Contact>()
        c.moveToFirst()
        while (c.moveToNext())  {
            val con = Contact(c)
            Log.d("AppDB", "getAll: $con")
            res.add(con)
        }

        return res
    }

    fun insert(c: Contact) {
        val db = writableDatabase
        Log.d("AppDB", "Inserting $c")
        db.insert("Contacts", null, c.toCvals())
    }

}