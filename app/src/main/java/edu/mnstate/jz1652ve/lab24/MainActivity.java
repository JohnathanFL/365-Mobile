package edu.mnstate.jz1652ve.lab24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    void swapFrag(int id, Fragment frag, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, frag, tag)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottomNav);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navCam: swapFrag(R.id.frameLayout, new CameraFragment(), CameraFragment.FRAG_TAG);
                break;
                case R.id.navGallery: swapFrag(R.id.frameLayout, new GalleryFragment(), GalleryFragment.FRAG_TAG);
                    break;
                case R.id.navUpload: swapFrag(R.id.frameLayout, new UploadFragment(), UploadFragment.FRAG_TAG);
                    break;
            }
            return true;
        });

    }
}
