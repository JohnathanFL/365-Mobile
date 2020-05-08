package edu.mnstate.jz1652ve.finalproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_loc_picker.*
import java.util.jar.Manifest
import kotlin.math.sqrt

const val TAG = "MapsActivity"
private val LOC_PERM_ID = 101

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var locMan: LocationManager
    public var mMap: GoogleMap? = null
    public lateinit var nav: NavigationView
    public lateinit var drawer: DrawerLayout
    lateinit var onChange: () -> Unit
    val dao = ContactHelper(this)
    var desiredLoc: LatLng? = null

    private var curScreen: Int = R.id.navMap

    /**
     * Lets us commit a fragment async while also doing something when it's ready
     * without extra cruft
     */
    private fun <F : Fragment> setFragment(f: F, then: (F) -> Unit = {}) = supportFragmentManager
        .beginTransaction()
        .replace(R.id.mainFrag, f as Fragment)
        .runOnCommit {
            then(supportFragmentManager.findFragmentById(R.id.mainFrag) as F)
        }
        .commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locMan = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        setContentView(R.layout.activity_maps)

        setSupportActionBar(findViewById(R.id.toolbar))

        setFragment(MapFragment()) { frag: MapFragment ->
            this.onChange = { addMarkers() }
            frag.getMapAsync(this)
        }

        drawer = findViewById(R.id.root)
        nav = findViewById(R.id.menu)
        nav.setNavigationItemSelectedListener { item ->
            if (item.itemId != this.curScreen) {
                when (item.itemId) {
                    R.id.navMap -> {
                        setFragment(MapFragment()) {
                            it.getMapAsync(this)
                        }
                    }
                    R.id.navList -> {
                        setFragment(ContactListFragment.newInstance()) { frag ->
                            this.onChange = { frag.notifyChanged() }
                            frag.goToMap = { loc ->
                                curScreen = R.id.navMap
                                desiredLoc = loc
                                setFragment(MapFragment()) {
                                    it.getMapAsync(this)

                                }
                            }
                        }
                        mMap = null
                    }
                }
                this.curScreen = item.itemId
            }

            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID

        val perm = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (perm == PackageManager.PERMISSION_GRANTED)
            mMap!!.isMyLocationEnabled = true
        else
            askForgiveness(android.Manifest.permission.ACCESS_FINE_LOCATION, LOC_PERM_ID)

        if (desiredLoc != null) {
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(desiredLoc))
            desiredLoc = null
        }

        addMarkers()
    }


    @SuppressLint("MissingPermission")
    fun addMarkers() {
        if (mMap == null) return
        val map = mMap!!

        map.clear()

        val allContacts = dao.getAll()
        if (map.isMyLocationEnabled)
            locMan.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    val otherLoc = Location(location)
                    var furthest = 0.0f
                    for (c in allContacts) {
                        otherLoc.latitude = c.lat
                        otherLoc.longitude = c.lng
                        val d = location!!.distanceTo(otherLoc)
                        if (d > furthest) furthest = d
                    }

                    map.addCircle(
                        CircleOptions()
                            .center(LatLng(location!!.latitude, location!!.longitude))
                            .radius(furthest.toDouble())
                            .strokeColor(Color.BLUE)
                            .fillColor(Color.argb(20, 0, 0, 255))
                    )
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String?) {}
                override fun onProviderDisabled(provider: String?) {}
            }, null)





        for (c in allContacts) {
            val pos = LatLng(c.lat, c.lng)
            map.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title(c.firstName + " " + c.lastName)
                    .snippet(c.phone)
            )
        }
    }

    fun onAddBtn(view: View) {
        var goAddItBoy = Intent(this, AddContactActivity::class.java)
        this.startActivityForResult(goAddItBoy, 0)
    }

    private fun askForgiveness(perm: String, id: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(perm), id)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOC_PERM_ID -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(
                        this,
                        getString(R.string.sphereDisabled),
                        Toast.LENGTH_SHORT
                    ).show()
                else if (mMap != null) { // i.e we're in the map fragment at the moment
                    val mapFragment =
                        supportFragmentManager.findFragmentById(R.id.mainFrag) as MapFragment
                    mapFragment.getMapAsync(this)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.onChange()
    }
}