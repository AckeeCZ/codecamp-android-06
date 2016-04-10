package com.codecamp.codecamp06.rest;

import com.codecamp.codecamp06.Config;
import com.codecamp.codecamp06.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Description for our books REST API.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/10/2016.
 */
public interface ApiDescription {

    @GET(Config.URL_BOOKS)
    Call<List<Book>> getBooks();
}
