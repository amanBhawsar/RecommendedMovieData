package com.example.codechefdataextractor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Data> data= Collections.emptyList();
    Data current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterData(Context context, List<Data> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.activity_container_data, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Data current = data.get(position);
        myHolder.textMovieName.setText(current.Title);
        myHolder.textURL.setText("Url: " + current.Url);
        myHolder.textGenere.setText("Genere: " + current.Genere);
        myHolder.textPrice.setText(current.Rating + " Star");
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        // load image into imageview using glide
        Glide.with(context).load(R.drawable.ic_movie_black_24dp)
                .placeholder(R.drawable.ic_movie_black_24dp)
                .error(R.drawable.ic_movie_black_24dp)
                .into(myHolder.ivMovie);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textMovieName;
        ImageView ivMovie;
        TextView textURL;
        TextView textGenere;
        TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textMovieName= (TextView) itemView.findViewById(R.id.textMovieName);
            ivMovie= (ImageView) itemView.findViewById(R.id.ivMovie);
            textURL = (TextView) itemView.findViewById(R.id.textURL);
            textGenere = (TextView) itemView.findViewById(R.id.textGenere);
            textPrice = (TextView) itemView.findViewById(R.id.textRating);
        }

    }

}