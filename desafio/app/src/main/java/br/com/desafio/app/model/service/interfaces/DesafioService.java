package br.com.desafio.app.model.service.interfaces;

import java.util.List;

import br.com.desafio.app.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DesafioService {

    @GET("list")
    Call<List<Movie>> listMovies (@Query("page") int page, @Query("size") int size);

    @GET("detail/{id}")
    Call<Movie> getDetail (@Path("id") String id);

}