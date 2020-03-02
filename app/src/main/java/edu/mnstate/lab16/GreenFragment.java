package edu.mnstate.lab16;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class GreenFragment extends Fragment {

    Button sendBtn;
    EditText msgText;
    OnGreenFragmentListener listener;

    public GreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_green, container, false);
        sendBtn = root.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.messageFromGreenFragment(msgText.getText().toString());
            }
        });
        msgText = root.findViewById(R.id.editMsg);
        return root;
    }

    interface OnGreenFragmentListener {
        void messageFromGreenFragment(String message);
    }

}
