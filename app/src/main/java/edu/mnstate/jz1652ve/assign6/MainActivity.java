package edu.mnstate.jz1652ve.assign6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    void swapFrag(Fragment frag, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragujo, frag, tag)
                .commit();
    }

    JobList jobList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navView);
        DrawerLayout layout = findViewById(R.id.root);

        this.jobList = new JobList();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navSearch:
                    swapFrag(jobList, JobList.FRAG_TAG);
                    layout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.navInfo:
                    swapFrag(new LicenseInfo(), "INFO");
                    layout.closeDrawer(GravityCompat.START);
                    break;
                default:
                    break;

            }

            return true;
        });
    }
}
