package edu.mnstate.jz1652ve.lab25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    void swapFrag(Fragment frag, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, frag, tag)
                .commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navView = findViewById(R.id.navView);
        DrawerLayout layout = findViewById(R.id.root);

        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navCam: swapFrag(new CameraFragment(),
                        CameraFragment.FRAG_TAG);
                layout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.navGallery: swapFrag(new GalleryFragment(),
                        GalleryFragment.FRAG_TAG);
                    layout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.navUpload: swapFrag(new UploadFragment(),
                        UploadFragment.FRAG_TAG);
                    layout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

    }
}
