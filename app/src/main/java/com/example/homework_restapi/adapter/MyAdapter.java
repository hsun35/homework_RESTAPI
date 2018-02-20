package com.example.homework_restapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework_restapi.R;
import com.example.homework_restapi.model.Simpsons;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hefen on 2/18/2018.
 */

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    List<Simpsons> simpsonsList;
    Context context;
    public static boolean isGrid;
    public static boolean doToggle;
    private PersonModifier personModifier;

    public interface PersonModifier{
        public void onPersonSelected(int position);
    }

    public MyAdapter(List<Simpsons> simpsonsList, Context context) {
        this.simpsonsList = simpsonsList;
        this.context = context;
    }

    public void setPersonModifier(PersonModifier personModifier){
        this.personModifier = personModifier;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;// = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        if (isGrid) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        }
        final MyViewHolder myViewHolder = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personModifier!=null){
                    personModifier.onPersonSelected(myViewHolder.getAdapterPosition());
                }
            }
        });

        return myViewHolder;
        //return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Simpsons simpson = simpsonsList.get(position);
        String imageUrl = simpson.getImageUrl();

        if (isGrid) {

            if (imageUrl == null || imageUrl.length() == 0) {
                holder.imageViewMyImage.setImageResource(R.drawable.simpsons);
            } else {
                Picasso.with(context).load(imageUrl).into(holder.imageViewMyImage);
            }
        } else {
            holder.nameTextView.setText(simpson.getName());
        }
        //holder.nameTextView.setText(simpson.getName());
        //holder.descriptionTextView.setText(simpson.getDescription());
        //holder.countryTextView.setText(actor.getCountry());



        //Picasso.with(context).load(actor.getImageUrl()).into(holder.imageViewMyImage);//take image url put on image view
    }

    @Override
    public int getItemCount() {
        return simpsonsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;// descriptionTextView, countryTextView;
        ImageView imageViewMyImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            if (isGrid) {
                imageViewMyImage = itemView.findViewById(R.id.imageView2);
            } else {
                nameTextView = itemView.findViewById(R.id.textViewName);
            }
            //nameTextView = itemView.findViewById(R.id.textViewName);
            //descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            //countryTextView = itemView.findViewById(R.id.textViewCountry);
            //imageViewMyImage = itemView.findViewById(R.id.imageView);
        }
    }


}
