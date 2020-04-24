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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;


public class LicenseInfo extends Fragment {

    public LicenseInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void openURL(int id) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(id)));
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View me = inflater.inflate(R.layout.fragment_license_info, container, false);

        NavigationView view = me.findViewById(R.id.infoNav);

        view.setNavigationItemSelectedListener(v -> {
            switch (v.getItemId()) {
                case R.id.retrofit: openURL(R.string.retrofitURL);
                break;
                case R.id.gson: openURL(R.string.gsonURL);
                break;
                default: break;
            }
            return true;
        });

        return me;
    }
}
