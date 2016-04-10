package com.codecamp.codecamp06.ui.fragment;

import com.codecamp.codecamp06.App;
import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.model.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Books list fragment that downloads data using Retrofit library.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/9/2016.
 */
public class RetrofitBooksListFragment extends BooksListFragment {
    public static final String TAG = RetrofitBooksListFragment.class.getName();

    @Override
    protected void loadBooks() {
        Call<List<Book>> call = App.getInstance().getApiDescription().getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, retrofit2.Response<List<Book>> response) {
                showProgress(false);
                onBooksLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                showProgress(false);
                // Handle error, maybe show some message to the user
            }
        });
    }

    @Override
    protected String getTitle() {
        return getString(R.string.retrofit);
    }
}
