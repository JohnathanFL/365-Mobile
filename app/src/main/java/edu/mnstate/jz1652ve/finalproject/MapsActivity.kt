package edu.mnstate.jz1652ve.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

private const val TAG = "MapsActivity"
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var nav: NavigationView
    private lateinit var drawer: DrawerLayout

    private fun <F: Fragment> setFragment(f: F, then: (F) -> Unit = {}) = supportFragmentManager
        .beginTransaction()
        .replace(R.id.mainFrag, f)
        .runOnCommit { then(supportFragmentManager.findFragmentById(R.id.mainFrag) as F) }
        .commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        setFragment(SupportMapFragment.newInstance()) { frag: SupportMapFragment ->
            frag.getMapAsync(this)
        }





        drawer = findViewById(R.id.root)
        nav = findViewById(R.id.menu)
        nav.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navMap -> {
                    setFragment(SupportMapFragment()) { frag: SupportMapFragment ->
                        frag.getMapAsync(this)
                    }
                    drawer.closeDrawer(GravityCompat.START)
                }
                R.id.navComp -> {
                    setFragment(CompanyList.newInstance())
                    mMap = null
                    drawer.closeDrawer(GravityCompat.START)
                }
                R.id.navStronghold -> {
                    drawer.closeDrawer(GravityCompat.START)
                }
            }

            true
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}