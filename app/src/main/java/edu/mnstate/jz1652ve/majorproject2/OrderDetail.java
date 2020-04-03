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
public class OrderDetail extends Fragment implements ItemInspector {

    Item curItem;
    TextView prodName, prodCat, orderQuant, prodPrice;

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
        this.orderQuant = me.findViewById(R.id.orderQuant);
        this.prodPrice = me.findViewById(R.id.prodPrice);

        return me;
    }

    void inspect() {
        this.prodName.setText(this.curItem.name);
        this.prodCat.setText(this.curItem.category);
        this.orderQuant.setText("" + this.curItem.quant);
        this.prodPrice.setText("" + this.curItem.price);
    }

    @Override
    public void inspect(Bundle clicked) {
        String name = clicked.getString("name");
        String list = clicked.getString("list");

        this.curItem = DBMan.getItem(this.getContext(), name, list);

        this.inspect();
    }
}
