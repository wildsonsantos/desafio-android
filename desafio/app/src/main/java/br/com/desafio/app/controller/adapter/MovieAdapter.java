package br.com.desafio.app.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.desafio.app.R;
import br.com.desafio.app.model.Movie;

public class MovieAdapter extends BaseAdapter {
    private Context context = null;
    private List<Movie> list = new ArrayList<Movie>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        View view;
        ViewHolder holder;

        if( convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


        holder.tvTitle.setText(movie.getName());
        Picasso.get()
                .load(movie.getUrl())
                .placeholder(R.drawable.small_image)
                .into(holder.ivCapa);

        return view;
    }

    public void addList(List<Movie> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public Movie getMovie(int i){
        return this.list.get(i);
    }

    class ViewHolder{

        ImageView ivCapa;
        TextView tvTitle;


        public ViewHolder(View view) {
            this.ivCapa = (ImageView) view.findViewById(R.id.ivCapa);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
