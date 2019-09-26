package com.example.myfirstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.imdb.Search;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;

public class IMDBAdapter extends RecyclerView.Adapter<IMDBAdapter.ImdbHolder> {

   List<Search> lstMovies;
    IMDBAdapter( List<Search> lst){
        lstMovies=lst;

    }

    @NonNull
    @Override
    public ImdbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.imdb_recycler_item,parent,false);
        ImdbHolder myHolder=new ImdbHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImdbHolder holder, int position) {
        String name= lstMovies.get(position).getTitle();
        String year=lstMovies.get(position).getYear();
        String poster=lstMovies.get(position).getPoster();
        holder.txtImdb.setText(name);
        holder.txtYear.setText(year);
        Picasso.get().load(poster).into(holder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return lstMovies.size();
    }

    class ImdbHolder extends RecyclerView.ViewHolder{
        TextView txtImdb;
        TextView txtYear;
        ImageView imgPoster;

        public ImdbHolder(@NonNull View itemView) {
            super(itemView);
            txtImdb=itemView.findViewById(R.id.txtImdbItemTitle);
            txtYear=itemView.findViewById(R.id.txtImdbItemYear);
            imgPoster=itemView.findViewById(R.id.imgImdbItemPoster);

        }
    }

}
