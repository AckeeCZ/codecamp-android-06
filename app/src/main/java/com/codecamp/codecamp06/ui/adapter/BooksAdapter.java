package com.codecamp.codecamp06.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.listener.OnItemClickListener;
import com.codecamp.codecamp06.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for books list.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/2/2016.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    public static final String TAG = BooksAdapter.class.getName();

    private List<Book> books;
    OnItemClickListener<Book> listener;

    public BooksAdapter(List<Book> books, OnItemClickListener<Book> listener) {
        this.books = books;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtAuthor;
        private View indicatorColor;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            indicatorColor = itemView.findViewById(R.id.indicator_color);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bindView(Book book) {
            indicatorColor.setBackgroundColor(Color.parseColor(book.getColor()));
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());

            if (book.getImage() != null) {
                image.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext()).load(book.getImage()).into(image);
            } else {
                image.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(books.get(getAdapterPosition()));
                }
            });
        }
    }
}
