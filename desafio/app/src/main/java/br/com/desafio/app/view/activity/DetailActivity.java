package br.com.desafio.app.view.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.desafio.app.R;
import br.com.desafio.app.controller.MainController;
import br.com.desafio.app.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivCapa;
    private TextView tvTitle, tvDescription;
    private ScrollView scrollViewDetail;
    private ProgressBar progressBarDetail;
    private MainController mainController = null;
    private String _id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent() != null){
            this._id = getIntent().getStringExtra("_id");
       }

        this.setUp();
    }

    private void setUp(){
        this.ivCapa = (ImageView) findViewById(R.id.ivCapa);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvDescription = (TextView) findViewById(R.id.tvDescription);
        this.scrollViewDetail = (ScrollView) findViewById(R.id.scrollViewDetail);
        this.progressBarDetail = (ProgressBar) findViewById(R.id.progressBarDetail);

        this.mainController = new MainController();
        this.onLoadMovieDetail(this._id);
    }

    private void onLoadMovieDetail(String id){
        this.scrollViewDetail.setVisibility(View.GONE);
        this.progressBarDetail.setVisibility(View.VISIBLE);

        this.mainController.movieDetail(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.body() != null){
                    onLoadConteudo(response.body());
                }

                scrollViewDetail.setVisibility(View.VISIBLE);
                progressBarDetail.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                scrollViewDetail.setVisibility(View.GONE);
                progressBarDetail.setVisibility(View.GONE);
                alert();
            }
        });
    }

    private void onLoadConteudo(Movie movie){
        Picasso.get()
                .load(movie.getUrl())
                .placeholder(R.drawable.small_image)
                .into(this.ivCapa);

        this.tvTitle.setText(movie.getName());
        this.tvDescription.setText(movie.getDescription());

    }

    private void alert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("Erro ao capturar o detalhamento. Tente novamente mais tarde.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
