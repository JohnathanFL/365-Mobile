package edu.mnstate.jz1652ve.finalproject

import android.content.Context
import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import java.sql.Date

@Entity
data class Company(
    @PrimaryKey val name: String,
    @ColumnInfo val leader: String,
    val ty: Char,
    val number: UInt) {
}

@Dao
interface CompanyDao {
    @Query("SELECT * FROM Company;")
    fun getAll(): List<Company>

    @Query("SELECT * FROM Company WHERE leader = (:leader);")
    fun getByLeader(leader: String): List<Company>

    @Query("SELECT * FROM Company WHERE name = (:name) LIMIT 1;")
    fun getByName(name: String): Company

    @Insert
    fun insertAll(vararg company: Company)
}

@Entity
data class Stronghold(
    @PrimaryKey val name: String,
    val lat: Double,
    val lng: Double,
    val number: Int
){
    public fun pos() = LatLng(lat,lng)
}

@Dao
interface StrongholdDao {
    @Query("SELECT * FROM Stronghold;")
    fun getAll(): List<Stronghold>

    @Query("SELECT * FROM Stronghold WHERE name like (:name)")
    fun getLikeName(name: String): List<Stronghold>

    @Query("SELECT * FROM Stronghold WHERE name = (:name) LIMIT 1;")
    fun getByName(name: String): Stronghold

    @Insert
    fun insertAll(vararg stronghold: Stronghold)
}

@Entity
data class Assault(
    @PrimaryKey val id: Int,
    val attackWhich: String,
    val attackWhen: Date
    ) {}

@Dao
interface AssaultDao {
    @Query("SELECT * FROM Assault;")
    fun getAll(): List<Assault>

    @Query("SELECT * FROM Assault WHERE attackWhich = (:fort);")
    fun getAttacksOnFort(fort: String): List<Assault>

    @Insert
    fun insertAll(vararg assault: Assault)
}

@Entity
data class Sortie(
    val assault: Int,
    val company: String,
    val lat: Double,
    val lng: Double
    ) {}

@Dao
interface SortieDao {
    @Query("SELECT * FROM Sortie;")
    fun getAll(): List<Sortie>

    @Query("SELECT * FROM Sortie WHERE assault = (:assaultID);")
    fun getByAssault(assaultID: Int): List<Sortie>

    @Insert
    fun insertAll(vararg sorties: Sortie)
}

@Database(entities = [Company::class, Stronghold::class, Assault::class, Sortie::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun companyDao(): CompanyDao
    abstract fun strongholdDao(): StrongholdDao
    abstract fun assaultDao(): AssaultDao
    abstract fun sortieDao(): SortieDao
}

// Singleton for AppDB
var appDB: AppDB? = null
public fun getAppDB(ctx: Context): AppDB {
    if(appDB == null) appDB = Room.databaseBuilder(ctx, AppDB::class.java, "db").build()

    return appDB as AppDB
}