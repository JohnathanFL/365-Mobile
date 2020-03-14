/**
 * Calculate the monthly payment amount for the purchase of a new computer with customization.
 * @author Johnathan Lee
 * @date 2020-03-16
 */

package edu.mnstate.assign5;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {
    private static final String TAG = "InputFragment";

    RadioGroup chosenGPU;
    CalendarView payoffDate;
    Spinner includedRAM;
    SeekBar extraBatteries;



    public InputFragment() {
        // Required empty public constructor
    }

    PriceConsumer consumer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        this.chosenGPU = view.findViewById(R.id.chosenGPU);
        this.chosenGPU.setOnCheckedChangeListener((group, i) -> {
            switch (i) {
                case R.id.choseAMD: consumer.setGPU(PriceConsumer.GPU.AMD);
                break;
                case R.id.choseIntel: consumer.setGPU(PriceConsumer.GPU.Intel);
                break;
                case R.id.choseNVIDIA: consumer.setGPU(PriceConsumer.GPU.NVIDIA);
                break;
            }
        });

        this.payoffDate = view.findViewById(R.id.payoffDate);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MONTH, 1);
        // Min of 1 month from now
        this.payoffDate.setMinDate(date.getTimeInMillis());
        this.payoffDate.setDate(date.getTimeInMillis());
        //if(this.payoffDate == null) return view;
        this.payoffDate.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            consumer.setDate(month, year);
        });

        this.includedRAM = view.findViewById(R.id.includedRAM);
        this.includedRAM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Since I have them set to go up by powers of 2 by each position....
                Log.d(TAG, "onItemSelected: " + position);
                consumer.setRAM(8 * (1 << position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.extraBatteries = view.findViewById(R.id.extraBatteries);
        this.extraBatteries.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                consumer.setNumBatteries(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

}
