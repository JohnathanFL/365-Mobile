/**
 * A simple grocery list app.
 *
 * Johnathan Lee
 * MSUM Mobile App Dev
 * Due 04/03/20
 */

package edu.mnstate.jz1652ve.majorproject2;

import android.content.ContentValues;
import android.database.Cursor;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Calendar;

public class Item {
    String name, list, category;
    int price, quant;
    Calendar getBy;

    public Item(String name, String list, String cat, int price, int quant, Calendar getBy) {
        this.name = name;
        this.list = list;
        this.category = cat;
        this.price = price;
        this.quant = quant;
        this.getBy = getBy;
    }

    public Item(Cursor c) {
        this.name = c.getString(c.getColumnIndex("name"));
        this.list = c.getString(c.getColumnIndex("list"));
        this.category = c.getString(c.getColumnIndex("category"));
        this.price = c.getInt(c.getColumnIndex("price"));
        this.quant = c.getInt(c.getColumnIndex("quant"));

        String rawGetBy = c.getString(c.getColumnIndex("getBy"));

        if(rawGetBy != null) {
            String[] date = c.getString(c.getColumnIndex("getBy")).split("-");

            this.getBy = Calendar.getInstance();
            this.getBy.set(Calendar.YEAR, Integer.parseInt(date[2]));
            this.getBy.set(Calendar.MONTH, Integer.parseInt(date[0]));
            this.getBy.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[1]));

        } else {
            this.getBy = null;
        }

    }

    public ContentValues toCVals() {
        ContentValues res = new ContentValues();
        res.put("name", this.name);
        res.put("category", this.category);
        res.put("list", this.list);
        res.put("price", this.price);
        res.put("quant", this.quant);
        if(this.getBy != null)
            res.put("getBy", String.format("%s-%s-%s", this.getBy.get(Calendar.MONTH), this.getBy.get(Calendar.DAY_OF_MONTH), this.getBy.get(Calendar.YEAR)));

        return res;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", list='" + list + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quant=" + quant +
                ", getBy=" + (getBy != null ? getBy.get(Calendar.YEAR) + "-" + getBy.get(Calendar.MONTH) + "-" + getBy.get(Calendar.DAY_OF_MONTH) : "null")+
                '}';
    }
}
