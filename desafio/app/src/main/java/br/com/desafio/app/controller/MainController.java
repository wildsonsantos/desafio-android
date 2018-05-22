package br.com.desafio.app.controller;

import java.util.List;

import br.com.desafio.app.model.Movie;
import br.com.desafio.app.model.service.NetworkHelper;
import br.com.desafio.app.model.service.interfaces.DesafioService;
import retrofit2.Call;

public class MainController {
    NetworkHelper networkHelper = null;
    DesafioService desafioService = null;


    public MainController() {
        this.networkHelper = new NetworkHelper();
        this.desafioService = this.networkHelper.retrofitProvider().create(DesafioService.class);
    }

    public Call<List<Movie>> movieList(int page, int size) {
        return desafioService.listMovies(page, size);
    }

    public Call<Movie> movieDetail(String id) {
        return desafioService.getDetail(id);
    }
}
