package edu.mnstate.lab16;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlueFragment extends Fragment {

    View root;

    public BlueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_blue, container, false);
        return root;
    }

    public void msgReceived(String message) {
        TextView txt = root.findViewById(R.id.msgReceiver);
        txt.setText(message);
    }

}
