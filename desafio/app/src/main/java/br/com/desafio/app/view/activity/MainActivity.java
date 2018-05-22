package br.com.desafio.app.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.desafio.app.R;
import br.com.desafio.app.controller.MainController;
import br.com.desafio.app.controller.adapter.MovieAdapter;
import br.com.desafio.app.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private MovieAdapter movieAdapter = null;
    private MainController mainController = null;
    private int sizeList = 10;
    private ProgressBar progressBarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUp();

    }

    private void setUp(){
        this.listView = (ListView) findViewById(R.id.listView);
        this.progressBarMain = (ProgressBar) findViewById(R.id.progressBarMain);
        this.movieAdapter = new MovieAdapter(this);

        this.listView.setAdapter(this.movieAdapter);
        this.listView.setOnItemClickListener(this);

        this.mainController = new MainController();

        listView.setVisibility(View.GONE);
        progressBarMain.setVisibility(View.VISIBLE);

        this.onLoadListMovie();
    }


    private void onLoadListMovie(){
        mainController.movieList(0, sizeList).enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieAdapter.addList(response.body());
                listView.setVisibility(View.VISIBLE);
                progressBarMain.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                alert();
            }
        });
    }


    private void alert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("Erro ao capturar a lista. Tente novamente mais tarde.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        onLoadListMovie();
                    }
                })
                .show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie m = movieAdapter.getMovie(position);
        Intent it = new Intent(MainActivity.this, DetailActivity.class);
        it.putExtra("_id", m.get_id());
        startActivity(it);
    }
}
