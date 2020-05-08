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

import android.os.Bundle
import android.view.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : SupportMapFragment() {
    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        val res = super.onCreateView(p0, p1, p2)
        setHasOptionsMenu(true)
        return res
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.maps_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activ = activity as MapsActivity
        when (item.itemId) {
            R.id.optHybrid -> {
                activ.mMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
            R.id.optSat -> {
                activ.mMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            R.id.optTerr -> {
                activ.mMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
            else -> TODO("No such option")
        }

        return true
    }
}