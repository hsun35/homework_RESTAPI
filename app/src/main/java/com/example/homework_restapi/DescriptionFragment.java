package com.example.homework_restapi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homework_restapi.model.Simpsons;
import com.squareup.picasso.Picasso;

/**
 * Created by hefen on 2/18/2018.
 */

public class DescriptionFragment extends Fragment {
    View rootView;
    TextView name;
    TextView description;
    ImageView image;
    Context context;
    int selectedSimpsonIndex;
    String simpsonName;
    String simpsonDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_description,container,false);
        initDescription();
        return rootView;
    }

    private void initDescription(){
        name = (TextView)rootView.findViewById(R.id.textViewName);
        description = (TextView)rootView.findViewById(R.id.textViewDescription);
        image = (ImageView)rootView.findViewById(R.id.imageView);
        context = rootView.getContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle == null) {
            Log.i("mylog", "bundle null");
        } else {
            Log.i("mylog", "bundle");
        }
        selectedSimpsonIndex = bundle.getInt("simpson_index", 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        String nm = Simpsons.simpsons.get(selectedSimpsonIndex).getName();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(nm);
        name.setText(nm);
        description.setText(Simpsons.simpsons.get(selectedSimpsonIndex).getDescription());
        String imageUrl = Simpsons.simpsons.get(selectedSimpsonIndex).getImageUrl();
        if (imageUrl == null || imageUrl.length() == 0) {
            return;
        }
        Picasso.with(context).load(imageUrl).into(image);
    }
}
