/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */
package edu.mnstate.jz1652ve.majorproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    static final String PREF_NAME = "edu.mnstate.jz1652ve.mp2.prefs";
    ListFragment listFrag;
    OrderDetail detailFrag;
    FloatingActionButton addBtn;

    PopupWindow addWindow;

    static final Set<String> defaultLists = new HashSet(){{
        add("Main");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        FragmentManager manager = getSupportFragmentManager();

        this.listFrag = (ListFragment)manager.findFragmentById(R.id.listFrag);
        this.detailFrag = (OrderDetail)manager.findFragmentById(R.id.detailFrag);
        this.addBtn = findViewById(R.id.addWindowBtn);

        this.listFrag.inspector = this.detailFrag;


        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        Set<String> lists = prefs.getStringSet("Lists", null);
        if(lists == null || lists.size() == 0) {
            Log.d(TAG, "onCreate: Defaulted lists");
            prefs.edit()
                    .putStringSet("Lists", defaultLists)
                    .commit();
        } else {
            Log.d(TAG, "listsList: " + lists.toString());
        }



        this.listFrag.dataChanged();

        this.addBtn.setOnClickListener(v -> {
            ViewGroup absRoot = findViewById(R.id.absRoot);

            LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.add_dialog, absRoot, false);
            this.addWindow = new PopupWindow(root, (int)(absRoot.getWidth() * 0.9), (int)(absRoot.getHeight() * 0.9));

            setupAddWindow(root);


            addWindow.setFocusable(true);
            addWindow.update();
            addWindow.showAtLocation(findViewById(R.id.absRoot), Gravity.CENTER, 0, 0);
        });

    }

    void setupAddWindow(View root) {
        EditText prodName = root.findViewById(R.id.prodName);
        Spinner completer = root.findViewById(R.id.completions);

        ArrayList<String> curList = DBMan.getItemsLikeName(this.getBaseContext(), "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_dropdown_item_1line, curList);
        completer.setAdapter(adapter);

        prodName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                adapter.clear();
                adapter.addAll(DBMan.getItemsLikeName(getBaseContext(), s.toString()).toArray(new String[]{}));
                adapter.notifyDataSetChanged();
            }
        });


        completer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prodName.setText(curList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RadioGroup radioGroup = root.findViewById(R.id.prodCat);

        EditText quantNum = root.findViewById(R.id.orderQuantNum), priceNum = root.findViewById(R.id.prodPriceNum);
        SeekBar quantSeek = root.findViewById(R.id.orderQuantSeek), priceSeek = root.findViewById(R.id.prodPriceSeek);

        CheckBox onMonday = root.findViewById(R.id.onMonday);
        DatePicker picker = root.findViewById(R.id.getBy);
        picker.setEnabled(false);
        picker.setAlpha(0.3f);
        picker.setFocusable(false);

        Button addBtn = root.findViewById(R.id.addBtn);

        onMonday.setOnCheckedChangeListener((v, checked) -> {
            picker.setEnabled(!checked);
            picker.setFocusable(!checked);
            picker.setAlpha(checked ? 0.3f : 1.0f);
        });
        
        addBtn.setOnClickListener(v -> {
            if(prodName.getText().length() == 0) {
                Toast.makeText(this, R.string.mustEnterProdName, Toast.LENGTH_SHORT).show();
                return;
            }

            String name = prodName.getText().toString();
            String category = ((RadioButton)root.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            int quant = Integer.parseInt(quantNum.getText().toString()),
                    // As always, store the price as ddddcc, where d is dollars and c is cents
                    price = (int)(Double.parseDouble(priceNum.getText().toString()) * 100);

            Calendar cal = Calendar.getInstance();
            cal.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());

            Log.d(TAG, "Category was " + category);
            DBMan.setItem(this.getBaseContext(), new Item(name, ListFragment.curList, category, price, quant, onMonday.isChecked() ? null : cal));
            ListFragment.dataChanged();

            addWindow.dismiss();
            addWindow = null;
        });

        quantNum.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    quantSeek.setProgress(Integer.parseInt(s.toString()));
                } else {
                    quantNum.setText(getResources().getText(R.string.defaultQuant));
                }
            }
        });

        quantSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) // Help avoid recursion
                    quantNum.setText("" + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        priceNum.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    double price = Double.parseDouble(s.toString());
                    priceSeek.setProgress((int)price);
                } else {
                    priceNum.setText(getResources().getText(R.string.defaultPrice));
                }
            }
        });
        priceSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) { // Help avoid recursion
                    priceNum.setText("" + progress + ".00");
                    Log.d(TAG, "onProgressChanged: " + priceNum.getText().toString() + " from " + progress);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    @Override
    public void onBackPressed() {
        if(this.addWindow != null) {
            this.addWindow.dismiss();
            this.addWindow = null;
        } else {
            super.onBackPressed();
        }
    }
}
