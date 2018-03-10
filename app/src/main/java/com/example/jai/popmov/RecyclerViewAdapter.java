package com.example.jai.popmov;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jaikh on 10-03-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    String[] rec_items;
    Context mContext;
    JSONArray multipleMovies;
    JSONObject singleMovie;

    //fields of a single movie object that we'll pass, add more fields here
    String title,poster;

    RecyclerViewAdapter(Context context,String[] i, JSONArray mm)
    {
        mContext = context;
        rec_items = i;
        multipleMovies = mm;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String currentString = rec_items[position];
        //we will use picasso here
        Picasso.get().load(currentString).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return rec_items.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        RecyclerViewAdapter rva;

        public RecyclerViewHolder(View itemView, RecyclerViewAdapter adapter) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            this.rva = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                singleMovie = multipleMovies.getJSONObject(getLayoutPosition());
                title = singleMovie.getString("title");
                poster = "http://image.tmdb.org/t/p/w185"+singleMovie.getString("poster_path");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext,DetailActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("picture",poster);
            mContext.startActivity(intent);
        }
    }
}
