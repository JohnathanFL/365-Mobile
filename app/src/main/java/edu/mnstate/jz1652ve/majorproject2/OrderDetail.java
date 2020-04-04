/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */

package edu.mnstate.jz1652ve.majorproject2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Displays info about a particular order
 */
public class OrderDetail extends Fragment implements ItemInspector {

    Item curItem;
    TextView prodName, prodCat, quantPrice;
    LinearLayout getBy;

    public OrderDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View me = inflater.inflate(R.layout.fragment_order_detail, container, false);

        this.prodName = me.findViewById(R.id.prodName);
        this.prodCat = me.findViewById(R.id.prodCat);
        this.quantPrice = me.findViewById(R.id.quantPrice);
        this.getBy = me.findViewById(R.id.getBy);


        return me;
    }

    void inspect() {
        if(this.curItem == null) {
            this.prodName.setText("");
            this.prodCat.setText("");
            this.quantPrice.setText("");
            this.getBy.removeAllViews();
        } else {
            this.prodName.setText(this.curItem.name);
            this.prodCat.setText(this.curItem.category);
            this.quantPrice.setText(String.format(getResources().getString(R.string.quantPrice), this.curItem.quant, this.curItem.price / 100 + (this.curItem.price % 100) / 100.0));

            if(this.curItem.getBy != null) {
                DatePicker picker = new DatePicker(this.getContext());
                picker.setFocusable(false);
                picker.setMaxDate(this.curItem.getBy.getTimeInMillis());
                picker.setMinDate(this.curItem.getBy.getTimeInMillis());
                this.getBy.removeAllViews();
                this.getBy.addView(picker);
            } else {
                this.getBy.removeAllViews();
                TextView view = new TextView(this.getContext());
                view.setText(getResources().getString(R.string.nextShoppingDay));
                this.getBy.addView(view);
            }
        }
    }

    @Override
    public void inspect(Bundle clicked) {
        String name = clicked.getString("name");
        String list = clicked.getString("list");

        boolean deleted = clicked.getBoolean("deleted");
        if(deleted && name.equals(this.curItem.name) && list.equals(this.curItem.list)) {
            this.curItem = null;
        }

        this.curItem = DBMan.getItem(this.getContext(), name, list);

        this.inspect();
    }
}
