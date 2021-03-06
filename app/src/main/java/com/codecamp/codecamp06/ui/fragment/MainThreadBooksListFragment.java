package com.codecamp.codecamp06.ui.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import com.codecamp.codecamp06.Config;
import com.codecamp.codecamp06.R;
import com.codecamp.codecamp06.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Books list fragment that loads books on main thread.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/9/2016.
 */
public class MainThreadBooksListFragment extends BooksListFragment{
    public static final String TAG = MainThreadBooksListFragment.class.getName();

    HttpURLConnection connection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void loadBooks() {
        List<Book> books = getBooksFromServer();
        showProgress(false);
        onBooksLoaded(books);
    }

    @Override
    protected String getTitle() {
        return getString(R.string.main_thread);
    }

    private List<Book> getBooksFromServer() {
        List<Book> books = null;

//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            URL url = new URL(Config.API_BASE_URL + Config.URL_BOOKS);

            connection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(connection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder result = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONArray json = new JSONArray(result.toString());

            books = parseJson(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return books;
    }

    private List<Book> parseJson(JSONArray array) throws JSONException {
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {

            JSONObject object = array.getJSONObject(i);

            Book book = new Book();

            book.setId(object.getInt("id"));
            book.setTitle(object.getString("title"));
            book.setAuthor(object.getString("author"));
            book.setGenre(object.getString("genre"));
            book.setColor(object.getString("color"));
            book.setImage(object.getString("image"));

            books.add(book);
        }

        return books;
    }
}
