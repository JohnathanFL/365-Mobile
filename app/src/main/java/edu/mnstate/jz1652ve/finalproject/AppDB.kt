/**
 * A "Sphere of Influence" application that stores contacts and tells you how much pull ya got.
 * Author: Johnathan Lee
 * Date: 05/08/2020
 * MSUM CSIS-365 Mobile, Spring 2020
 *
 *
 * This project is under the public domain.
 */
package edu.mnstate.jz1652ve.finalproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

public fun relFromInt(i: Int): RelType = when (i) {
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


    public fun toInt(): Int = when (this) {
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

data class Contact(
    val firstName: String,
    val lastName: String,
    val sex: Char,
    val married: Boolean,
    val rel: Int,
    val birthday: String,
    val phone: String,
    val lat: Double,
    val lng: Double,
    val id: Int? = null
) {
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
    ) {
    }

    fun toCvals(): ContentValues {
        val res = ContentValues()
        res.put("firstName", firstName)
        res.put("lastName", lastName)
        res.put("sex", sex.toInt())
        res.put(
            "married", if (married) {
                1
            } else {
                0
            }
        )
        res.put("rel", rel)
        res.put("birthday", birthday)
        res.put("phone", phone)
        res.put("lat", lat)
        res.put("lng", lng)
        if (id != null) res.put("id", id)

        return res
    }

    public fun honorific(context: Context) = when (this.sex) {
        'M' -> context.getString(R.string.maleHonorific)
        'F' ->
            if (this.married)
                context.getString(R.string.femMarried)
            else
                context.getString(R.string.femUnmarried)

        else -> TODO("Unreachable")
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

    private fun toList(c: Cursor): List<Contact> {
        val res = mutableListOf<Contact>()
//        c.moveToFirst()
        while (c.moveToNext()) {
            val con = Contact(c)
            Log.d("AppDB", "getAll: $con")
            res.add(con)
        }

        return res
    }

    fun getAll(): List<Contact> {
        Log.d("AppDB", "getAll")
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM Contacts;", arrayOf())
        return toList(c)
    }

    fun getSortFName(): List<Contact> {
        Log.d("AppDB", "getSortFName")
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM Contacts ORDER BY firstName;", arrayOf())
        return toList(c)
    }

    fun getSortLName(): List<Contact> {
        Log.d("AppDB", "getSortFName")
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM Contacts ORDER BY lastName;", arrayOf())
        return toList(c)
    }

    fun getLike(s: String): List<Contact> {
        Log.d("AppDB", "getLike " + s)
        val db = readableDatabase
        val arg = "%$s%"
        val c = db.rawQuery(
            "SELECT * FROM Contacts WHERE (firstname like ?) or (lastName like ?);",
            arrayOf(arg, arg)
        )
        return toList(c)
    }

    fun getByID(i: Int): Contact {
        Log.d("AppDB", "Getting ID#" + i)
        val db = readableDatabase
        val c = db.rawQuery("SELECT * FROM Contacts WHERE id = $i;", arrayOf())
//        if(!c.moveToFirst()) return null
        return Contact(c)
    }

    fun getByRel(rel: RelType): List<Contact> {
        Log.d("AppDB", "getByRel: $rel")
        val db = readableDatabase

        val relInt = rel.toInt()
        val c = db.rawQuery("SELECT * FROM Contacts WHERE rel = $relInt;", arrayOf())
        return toList(c)
    }

    fun insert(c: Contact) {
        val db = writableDatabase
        Log.d("AppDB", "Inserting $c")
        Log.d("AppDB", "Return: " + db.insert("Contacts", null, c.toCvals()))
    }

    fun delete(i: Int) {
        val db = writableDatabase
        Log.d("AppDB", "Deleting #$i")
        db.delete("Contacts", "id = ?", arrayOf("" + i))
    }

}