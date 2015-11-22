package com.hilfritz.joke.backend;

/**
 * Created by hilfritz.p.camallere on 7/28/2015.
 */
public class Joke {
    long id;
    String joke;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public Joke() {

    }

    public Joke(String joke) {
        this.joke = joke;
    }
}
