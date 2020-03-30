package edu.mnstate.jz1652ve.majorproject2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;


/**
 * Displays the different lists
 */
public class ListFragment extends Fragment {

    // Our root
    View me;
    Spinner listSelector;
    RecyclerView list;


    Overlord overlord;
    Contextable menu;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.me = inflater.inflate(R.layout.fragment_list, container, false);

        this.listSelector = this.me.findViewById(R.id.listSelector);
        this.list = this.me.findViewById(R.id.list);

        return this.me;
    }
}
