package edu.mnstate.jz1652ve.majorproject2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Displays info about a particular order
 */
public class OrderDetail extends Fragment {

    TextView prodName, prodCat, orderQuant, prodPrice, prodDesc;

    Overlord overlord;
    Contextable menu;

    public OrderDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }
}
