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
