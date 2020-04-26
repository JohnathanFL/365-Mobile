/**
 * Author: Johnathan Lee
 * A simple example of using Google Maps
 * Due 04/26/20
 * MSUM CS365 - Assign 7
 */

package edu.mnstate.jz1652ve.assign7;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOC_PERM_ID = 101;

    private TextView loc;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loc = findViewById(R.id.loc);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(mMap == null) return;

        int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(perm == PackageManager.PERMISSION_GRANTED)
            mMap.setMyLocationEnabled(true);
        else
            askForgiveness(Manifest.permission.ACCESS_FINE_LOCATION, LOC_PERM_ID);

        UiSettings settings = mMap.getUiSettings();
        settings.setMapToolbarEnabled(true);

        settings.setRotateGesturesEnabled(true);
        settings.setTiltGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setZoomGesturesEnabled(true);

        settings.setZoomControlsEnabled(true);

        mMap.setBuildingsEnabled(true);



        mMap.setOnCameraMoveListener(() -> {
            CameraPosition pos = mMap.getCameraPosition();
            loc.setText(String.format("%s | %s", pos.target.latitude, pos.target.longitude));
        });

        newMarker("Living on a Prayer", 45, 45);
        newMarker("Copenhagen", 55.6604556, 12.551956);
        newMarker("Minneapolis", 44.97764038, -93.263179175);
        newMarker("Keflavik Airport", 63.97869201, -22.6418087258);
        newMarker("The End of the Line on the Final Journey", 47.470556, 12.139722);
        newMarker("Shiroyama",31.6, 130.55);

        mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(55.6604556, 12.551956))
                .add(new LatLng(63.97869201, -22.6418087258))
                .add(new LatLng(44.97764038, -93.263179175))
                .geodesic(true)
        );

        mMap.addCircle(new CircleOptions()
                .strokeColor(Color.BLUE)
                .strokeWidth(5.0f)
                .center(new LatLng(31.6, 130.55))
                .radius(500.0)
        );

        mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(25.775278, -80.208889))
                .add(new LatLng(18.406389, -66.063889))
                .add(new LatLng(32.3, -64.783333))
                .fillColor(Color.argb(20, 0, 0, 255))
                .strokeWidth(1.0f)
        );

    }

    /**
     * Better to ask forgiveness than permission
     * @param perm
     * @param id
     */
    public void askForgiveness(String perm, int id) {
        ActivityCompat.requestPermissions(this, new String[]{perm}, id);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOC_PERM_ID:
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Can't show permission - permission required", Toast.LENGTH_SHORT).show();
                } else {
                    SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
        }
    }

    public void newMarker(String name, double lat, double lng) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name));
    }

    public void setSat(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void setHybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void setTerrain(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
}
