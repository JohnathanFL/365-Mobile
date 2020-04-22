package edu.mnstate.jz1652ve.lab27;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 101;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Can't show location - permission required", Toast.LENGTH_SHORT).show();
                }
                else {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng msumPos = new LatLng(46.8663522, -96.7620251);

        mMap = googleMap;

        UiSettings mapSettings = mMap.getUiSettings();

        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setZoomGesturesEnabled(true);
        mapSettings.setScrollGesturesEnabled(true);
        mapSettings.setTiltGesturesEnabled(true);
        mapSettings.setRotateGesturesEnabled(true);

        Marker msum = mMap.addMarker(new MarkerOptions().position(msumPos).title("MSUM").snippet("Minnesota State University Moorhead"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("0x0").snippet("We be zeroed out"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(45, 45)).title("Halfsies").snippet("Living on a prayer"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(55.673611, 12.568333)).title("Tivoli").snippet("Have a blast"));

        CameraPosition camPos = CameraPosition.builder()
                .target(msumPos)
                .zoom(50)
                .bearing(70)
                .tilt(25)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));

        mMap.addCircle(new CircleOptions().center(msumPos).radius(500.0).strokeWidth(10f).strokeColor(Color.BLUE).fillColor(Color.argb(70, 10, 50, 150)));


        if(mMap != null) {
            int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if(perm == PackageManager.PERMISSION_GRANTED)
                mMap.setMyLocationEnabled(true);
            else
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }
}
