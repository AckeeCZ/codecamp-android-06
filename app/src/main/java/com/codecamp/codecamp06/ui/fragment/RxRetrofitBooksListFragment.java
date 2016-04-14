package com.codecamp.codecamp06.ui.fragment;

import com.codecamp.codecamp06.App;
import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Books list fragment that downloads data using Retrofit library.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/9/2016.
 */
public class RxRetrofitBooksListFragment extends BooksListFragment {
    public static final String TAG = RxRetrofitBooksListFragment.class.getName();

    @Override
    protected void loadBooks() {
        App.getInstance().getApiDescription().getBooksRx()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    showProgress(false);
                    onBooksLoaded(list);
                }, throwable -> showProgress(false));
    }

    @Override
    protected String getTitle() {
        return getString(R.string.retrofit);
    }
}
