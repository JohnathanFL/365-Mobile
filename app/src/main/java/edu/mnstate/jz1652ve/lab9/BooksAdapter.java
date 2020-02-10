package edu.mnstate.jz1652ve.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class BooksAdapter extends BaseAdapter {
    private final Context ctx;
    private Book[] books;

    public BooksAdapter(Context ctx, Book[] books) {
        this.ctx = ctx;
        this.books = books;
    }

    @Override
    public int getCount() {
        return this.books.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = this.books[position];

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.ctx);
            convertView = inflater.inflate(R.layout.book_template, null);

            ImageView
                    bookImg = convertView.findViewById(R.id.imageview_cover_art),
                    favImg = convertView.findViewById(R.id.imageview_favorite);
            TextView
                    bookName = convertView.findViewById(R.id.textview_book_name),
                    bookAuthor = convertView.findViewById(R.id.textview_book_author);
            convertView.setTag(new ViewHolder(bookName, bookAuthor, bookImg, favImg));
        }


        ViewHolder view = (ViewHolder)convertView.getTag();

        view.img.setImageResource(book.image);
        //Picasso.with(this.ctx).load("https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/The_C_Programming_Language_logo.svg/1200px-The_C_Programming_Language_logo.svg.png").into(view.img);
        view.nameText.setText(book.name);
        view.authorText.setText(book.author);
        view.favImg.setImageResource(
                book.favorite ? android.R.drawable.star_on : android.R.drawable.star_off
        );

        return convertView;
    }

    public class ViewHolder {
        private final TextView nameText, authorText;
        private  final ImageView img, favImg;


        public ViewHolder(TextView nameText, TextView authorText, ImageView img, ImageView favImg) {
            this.nameText = nameText;
            this.authorText = authorText;
            this.img = img;
            this.favImg = favImg;
        }
    }
}
