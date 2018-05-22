package br.com.desafio.app.model.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    public NetworkHelper() {

    }
    public Retrofit retrofitProvider() {
        return new Retrofit.Builder()
                .baseUrl("https://desafio-mobile-pitang.herokuapp.com/movies/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
