package edu.mnstate.jz1652ve.majorproject2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContextMenu extends Fragment implements Contextable {

    public ContextMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_context_menu, container, false);
    }

    @Override
    public int addAction(String name, ContextAction action) {
        return 0;
    }

    @Override
    public void changeAction(int id, ContextAction action) {

    }

    @Override
    public void clearActions() {

    }
}
