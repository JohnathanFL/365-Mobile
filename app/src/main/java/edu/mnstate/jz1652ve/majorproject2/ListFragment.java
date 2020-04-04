/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */

package edu.mnstate.jz1652ve.majorproject2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;


/**
 * Displays the different lists
 */
public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";

    // Our root
    View me;
    Spinner listSelector;
    RecyclerView list;
    ImageButton addListBtn, delListBtn;

    String[] lists;

    // Because it's a headache trying to play catch-up with android and its "well maybe we'll just
    // re-create the instance without telling you, maybe we won't" crap. Just go static.
    // Ooooo take me down to the sane-GUI city where the GTK is green and the Qt is pretty
    static ItemInspector inspector;
    static ListFragment instance;
    static String curList;

    static void dataChanged() {
        ((ItemAdapter)instance.list.getAdapter()).refresh();
    }


    public ListFragment() {
        // Required empty public constructor
        // Because android's SDK is stupid.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        // Inflate the layout for this fragment
        this.me = inflater.inflate(R.layout.fragment_list, container, false);

        this.listSelector = this.me.findViewById(R.id.listSelector);
        this.list = this.me.findViewById(R.id.list);
        this.addListBtn = this.me.findViewById(R.id.addListBtn);
        this.delListBtn = this.me.findViewById(R.id.delListBtn);

        this.addListBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            EditText newListTxt = new EditText(this.getContext());
            builder.setView(newListTxt);
            builder.setPositiveButton(android.R.string.ok, (d, btn) -> {
                String name = newListTxt.getText().toString();
                if(name.isEmpty()) {
                    d.dismiss();
                    return;
                }

                SharedPreferences prefs = this.getContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                Set<String> lists = prefs.getStringSet("Lists", MainActivity.defaultLists);
                lists.add(name);

                prefs.edit()
                        .clear()
                        .putStringSet("Lists", lists)
                        .commit();

                lists = prefs.getStringSet("Lists", null);
                Log.d(TAG, "listsList: " + lists.toString());

                this.lists = lists.toArray(new String[]{});

                this.listSelector.setAdapter(new ArrayAdapter<>(this.me.getContext(), android.R.layout.simple_list_item_1, this.lists));

                // Preserve the old selection

                // Senior and still writing linear searches. :(
                int index = 0;
                for(int i = 0; i < this.lists.length; i++) {
                    if(this.lists[i].equals(curList)) index = i;
                }
                this.listSelector.setSelection(index);
                d.dismiss();
            });
            builder.show();
        });

        this.delListBtn.setOnClickListener(v -> {
            String curList = this.lists[this.listSelector.getSelectedItemPosition()];
            SharedPreferences prefs = this.getContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
            Set<String> allLists = prefs.getStringSet("Lists", null);

            allLists.remove(curList);
            prefs.edit()
                    .clear()
                    .putStringSet("Lists", allLists)
                    .commit();

            this.listSelector.setSelection(0);
            this.lists = allLists.toArray(new String[]{});
            this.listSelector.setAdapter(new ArrayAdapter<>(this.me.getContext(), android.R.layout.simple_list_item_1, this.lists));
            DBMan.delList(this.getContext(), curList);
        });

        //DBMan.setItem(this.getContext(), new Item("Apples", "Main", 999, 2, Calendar.getInstance()));

        this.list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ItemAdapter adapter = new ItemAdapter(this.getContext());
        this.list.setAdapter(adapter);


        SharedPreferences prefs = this.me.getContext().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        Set<String> allLists = prefs.getStringSet("Lists", null);
        if(allLists.size() == 0) {
            allLists.add("Main");
            prefs.edit()
                    .clear()
                    .putStringSet("Lists", allLists)
                    .commit();
        }
        this.lists = allLists.toArray(new String[]{});
        curList = this.lists[0];

        this.listSelector.setAdapter(new ArrayAdapter<String>(this.me.getContext(), android.R.layout.simple_list_item_1, this.lists));
        this.listSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curList = ListFragment.this.lists[position];
                adapter.changeToList(curList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return this.me;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        Context ctx;
        String curList;
        ArrayList<Item> items;

        ItemAdapter(Context ctx) {
            this.ctx = ctx;
            this.changeToList("Main");
        }

        public void refresh() {
            this.changeToList(this.curList);
        }

        public void changeToList(String list) {
            this.curList = list;
            this.items = DBMan.getItemsByList(ctx, list);
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(this.ctx).inflate(R.layout.list_item, parent, false);
            ViewHolder res = new ViewHolder(root);

            return res;
        }

        View lastSelected = null;
        @Override
        public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
            Item item = this.items.get(position);
            holder.itemName.setText(item.name);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            // /10 to get the dollars, %10 to get cents. Integer division on the first is intentional
            if(item.getBy != null) holder.itemDate.setText(item.getBy.get(Calendar.YEAR) + "-" + (item.getBy.get(Calendar.MONTH) + 1) + "-" + item.getBy.get(Calendar.DAY_OF_MONTH));
            else holder.itemDate.setText("");


            holder.view.setOnClickListener(v -> {
                if(lastSelected == holder.view)
                    return;

                Bundle bun = new Bundle();
                bun.putString("name", item.name);
                bun.putString("list", item.list);
                inspector.inspect(bun);
                Log.d(TAG, "Clicked " + item.toString());
                holder.view.setBackgroundColor(getResources().getColor(R.color.selectedBg));
                if(lastSelected != null)
                    lastSelected.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                lastSelected = holder.view;
            });
            holder.delBtn.setOnClickListener(v -> {
                DBMan.delItem(holder.view.getContext(), item.name, item.list);
                Bundle bun = new Bundle();
                bun.putString("name", item.name);
                bun.putString("list", item.list);
                bun.putBoolean("delete", true);
                inspector.inspect(bun);
                dataChanged();
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView itemName, itemDate;
            ImageButton delBtn;

            ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.view = itemView;
                this.itemName = itemView.findViewById(R.id.itemName);
                this.itemDate = itemView.findViewById(R.id.itemDate);
                this.delBtn = itemView.findViewById(R.id.delBtn);
            }
        }
    }
}

