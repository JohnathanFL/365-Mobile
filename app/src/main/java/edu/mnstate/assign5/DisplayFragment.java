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
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements PriceConsumer {
    private static final String TAG = "DisplayFragment";

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

    // Because it's tiring to write "new Bigdecimalblahblahblah"
    private static BigDecimal dec(float amt) {
        return new BigDecimal(amt);
    }
    private static BigDecimal dec(int amt) {
        return new BigDecimal(amt);
    }

    private void recalculate() {
        // Default price of 400
        BigDecimal totalPrice = new BigDecimal(400);

        switch (this.gpu) {
            case AMD:
                totalPrice = totalPrice.add(dec(150));
                this.gpuPrice.setText(R.string.amdCost);
                break;
            case Intel:
                totalPrice = totalPrice.add(dec(0));
                this.gpuPrice.setText(R.string.noCharge);
                break;
            case NVIDIA:
                totalPrice = totalPrice.add(dec(400));
                this.gpuPrice.setText(R.string.nvidiaCost);
                break;
        }

        // Only allowing RAM as 8, 16, 32, or 64
        switch (this.ram) {
            case 8:
                totalPrice = totalPrice.add(dec(0));
                this.ramPrice.setText(R.string.noCharge);
                break;
            case 16:
                totalPrice = totalPrice.add(dec(45));
                this.ramPrice.setText(R.string.ram16Cost);
                break;
            case 32:
                totalPrice = totalPrice.add(dec(90));
                this.ramPrice.setText(R.string.ram32Cost);
                break;
            case 64:
                totalPrice = totalPrice.add(dec(120));
                this.ramPrice.setText(R.string.ram64Cost);
                break;
        }

        this.batteryPrice.setText("$" + (this.numBatteries * 35)); // $35/battery
        totalPrice = totalPrice.add(dec(this.numBatteries * 35));


        Calendar cal = Calendar.getInstance();
        int curMonth = cal.get(Calendar.MONTH);
        int curYear = cal.get(Calendar.YEAR);

        curMonth += curYear * 12;
        int monthDiff = month - curMonth;
        monthDiff = Math.max(monthDiff, 1);
        numMonths.setText("" + monthDiff);

        Log.d(TAG, "recalculate: " + totalPrice);
        monthlyPayment.setText("$" + totalPrice.divide(dec(monthDiff), 2, RoundingMode.FLOOR));
    }

    @Override
    public void setAll(GPU gpu, int ram, int numBats, int month, int year) {
        this.gpu = gpu;
        this.ram = ram;
        this.numBatteries = numBats;
        this.month = month;
        this.month = month + year * 12;
        this.recalculate();
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
