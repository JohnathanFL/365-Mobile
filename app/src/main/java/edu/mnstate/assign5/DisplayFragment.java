package edu.mnstate.assign5;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements PriceConsumer {

    GPU gpu = GPU.Intel;
    int ram = 8, numBatteries = 1;
    int month;


    TextView gpuPrice, ramPrice, batteryPrice, numMonths, monthlyPayment;

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        this.gpuPrice = view.findViewById(R.id.priceGPU);
        this.ramPrice = view.findViewById(R.id.priceRAM);
        this.batteryPrice = view.findViewById(R.id.priceBatteries);
        this.numMonths = view.findViewById(R.id.numMonths);
        this.monthlyPayment = view.findViewById(R.id.monthlyPayment);

        return view;
    }

    private static BigDecimal dec(float amt) {
        return new BigDecimal(amt);
    }

    private void recalculate() {
        BigDecimal totalPrice = new BigDecimal(0);

        switch (this.gpu) {
            case AMD:
                totalPrice.add(dec(150.0f));
                this.gpuPrice.setText("+$150");
                break;
            case Intel:
                totalPrice.add(dec(0.0f));
                this.gpuPrice.setText(R.string.noCharge);
                break;
            case NVIDIA:
                totalPrice.add(dec(400.0f));
                this.gpuPrice.setText("+$400");
                break;
        }

        // Only allowing RAM as 8, 16, 32, or 64
        switch (this.ram) {
            case 8:
                totalPrice.add(dec(0.0f));
                this.ramPrice.setText(R.string.noCharge);
                break;
            case 16:
                totalPrice.add(dec(45.0f));
                this.ramPrice.setText("+$45");
                break;
            case 32:
                totalPrice.add(dec(90.0f));
                this.ramPrice.setText("+$90");
                break;
            case 64:
                totalPrice.add(dec(120.0f));
                this.ramPrice.setText("+$120");
                break;
        }

        this.batteryPrice.setText("$" + (this.numBatteries * 35)); // $35/battery
        totalPrice.add(dec(this.numBatteries * 35));


        Calendar cal = Calendar.getInstance();
        int curMonth = cal.get(Calendar.MONTH);
        int curYear = cal.get(Calendar.YEAR);

        curMonth += curYear * 12;
        int monthDiff = month - curMonth;
        monthDiff = Math.max(monthDiff, 1);
        numMonths.setText("" + monthDiff);

        monthlyPayment.setText("$" + totalPrice.divide(dec(monthDiff)));
    }

    @Override
    public void setGPU(GPU gpu) {
        this.gpu = gpu;
        this.recalculate();
    }

    @Override
    public void setRAM(int amt) {
        this.ram = amt;
        this.recalculate();
    }

    @Override
    public void setNumBatteries(int num) {
        this.numBatteries = num;
        this.recalculate();
    }

    @Override
    public void setDate(int month, int year) {
        this.month = month + year * 12;
        this.recalculate();
    }
}
