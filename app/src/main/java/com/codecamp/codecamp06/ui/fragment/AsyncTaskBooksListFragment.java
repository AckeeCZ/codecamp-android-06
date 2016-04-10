package com.codecamp.codecamp06.ui.fragment;

import android.os.AsyncTask;
import android.util.Log;

import com.codecamp.codecamp06.Config;
import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.listener.GetBooksListener;
import com.codecamp.codecamp06.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Books list fragment that downloads data using AsyncTask, OkHttp and GSON.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/9/2016.
 */
public class AsyncTaskBooksListFragment extends BooksListFragment {
    public static final String TAG = AsyncTaskBooksListFragment.class.getName();

    @Override
    protected void loadBooks() {
        GetBooksTask task = new GetBooksTask(this);
        task.execute();
    }

    @Override
    protected String getTitle() {
        return getString(R.string.async_task);
    }

    public static class GetBooksTask extends AsyncTask<Void, Void, List<Book>> {
        public static final String TAG = GetBooksTask.class.getName();
        private static final String URL_BOOKS = "/books";

        OkHttpClient client = new OkHttpClient();
        WeakReference<GetBooksListener> weakListener;

        public GetBooksTask(GetBooksListener listener) {
            weakListener = new WeakReference<>(listener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weakListener.get() != null) {
                weakListener.get().showProgress(true);
            }
        }

        @Override
        protected List<Book> doInBackground(Void... params) {

            List<Book> books = null;

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            try {
                URL url = new URL(Config.API_BASE_URL + URL_BOOKS);

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                books = parseJson(response.body().string());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            if (weakListener.get() != null) {
                weakListener.get().onBooksLoaded(books);
                weakListener.get().showProgress(false);
            }
        }

        private List<Book> parseJson(String json) throws JSONException {
            Type listType = new TypeToken<List<Book>>() {}.getType();
            return new Gson().fromJson(json, listType);
        }
    }
}
