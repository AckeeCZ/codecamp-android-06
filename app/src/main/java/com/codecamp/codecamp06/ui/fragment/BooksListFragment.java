package com.codecamp.codecamp06.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.listener.GetBooksListener;
import com.codecamp.codecamp06.listener.OnItemClickListener;
import com.codecamp.codecamp06.model.Book;
import com.codecamp.codecamp06.ui.adapter.BooksAdapter;
import com.codecamp.codecamp06.ui.utils.DividerItemDecoration;

import java.util.List;

/**
 * Fragment with books list.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/9/2016.
 */
public abstract class BooksListFragment extends Fragment implements GetBooksListener {
    public static final String TAG = BooksListFragment.class.getName();
    private static final String KEY_SHOW_PROGRESS = "show_progress";

    RecyclerView recyclerView;
    ProgressBar progressBar;

    BooksAdapter adapter;

    Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getTitle());

        recyclerView = (RecyclerView) getActivity().findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        progressBar = (ProgressBar) getActivity().findViewById(android.R.id.progress);
        if (savedInstanceState !=null) {
            progressBar.setVisibility(savedInstanceState.getBoolean(KEY_SHOW_PROGRESS) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SHOW_PROGRESS, progressBar.getVisibility() == View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_books_list, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                showProgress(true);
                loadBooks();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void loadBooks();

    protected abstract String getTitle();

    @Override
    public void showProgress(boolean show) {
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        menu.findItem(R.id.action_download).setEnabled(!show);
    }

    @Override
    public void onBooksLoaded(List<Book> books) {
        Log.d(TAG, "onBooksLoaded: ");
        adapter = new BooksAdapter(books, new OnItemClickListener<Book>() {
            @Override
            public void onItemClick(Book item) {
                Snackbar.make(recyclerView, "Book \"" + item.getTitle() + "\" clicked.", Snackbar.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
