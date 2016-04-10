package com.codecamp.codecamp06.listener;

import com.codecamp.codecamp06.model.Book;

import java.util.List;

/**
 * Listener for get books async task.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/3/2016.
 */
public interface GetBooksListener {
    void showProgress(boolean show);
    void onBooksLoaded(List<Book> books);
}
