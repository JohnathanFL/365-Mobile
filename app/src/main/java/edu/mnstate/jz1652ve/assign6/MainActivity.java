/**
 * Author: Johnathan Lee
 * Class: CSIS365 Mobile, MSUM
 * Due: 04/24/20
 * Assign 6 - A simple Github Jobs client that searches jobs and lets you open them in a browser.

 Copyright 2020 Johnathan Lee

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

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
        swapFrag(jobList, JobList.FRAG_TAG);

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
