package com.example.homework_restapi;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.homework_restapi.adapter.MyAdapter;
import com.example.homework_restapi.model.Simpsons;

/**
 * Created by hefen on 2/18/2018.
 */

public class SimpsonsFragment extends android.support.v4.app.Fragment implements MyAdapter.PersonModifier{
    View rootView;//used to init listview
    RecyclerView recyclerViewSimpsons;
    Context context;
    MyAdapter adapter;
    SendMessage sendMessage;
    RecyclerView.LayoutManager layoutManager;
    //boolean toogle;
    //boolean isGrid;
    //Toolbar mActionBarToolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_simpsons,container,false);
        //this.isGrid = MyAdapter.isGrid;
        Log.i("mylog", "on create fragment");
        initList();

        return rootView;
    }

    @Override
    public void onResume() {
        //if (this.isGrid != MyAdapter.isGrid) {
        //    sendMessage.sendToggle();
        //}

        super.onResume();
        //mActionBarToolbar = rootView.findViewById(R.id.toolbar);
        //setSupportActionBar(mActionBarToolbar);
        //mActionBarToolbar.setTitle("Simpsons Characters");
        //context.getSupportActionBar().setTitle(countryName);
        //sendMessage.setToolbar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Simpsons Characters");
        Log.i("mylog", "frag resume");
        //sendMessage.sendToggle();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initList() {

        context = rootView.getContext();
        recyclerViewSimpsons = rootView.findViewById(R.id.recyclerView);
        if (MyAdapter.isGrid){
            recyclerViewSimpsons.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            recyclerViewSimpsons.setLayoutManager(new LinearLayoutManager(context));
        }
        recyclerViewSimpsons.setHasFixedSize(true);

        //Bundle bundle = getArguments();
        //if (bundle == null) {
        //    Log.i("mylog", "bundle null");
        //} else {
        //    Log.i("mylog", "bundle");
        //}
        //isGrid = bundle.getBoolean("grid_mode", false);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewSimpsons.getContext(),
        //       layoutManager.);
        //recyclerViewSimpsons.addItemDecoration(dividerItemDecoration);

        adapter = new MyAdapter(Simpsons.simpsons, context);
        adapter.setPersonModifier(this);//??this context
        recyclerViewSimpsons.setAdapter(adapter);

        Log.i("mylog", "init list");
        //countriesAdapter = new ArrayAdapter<String>(context, R.layout.item_layout, R.id.textView, countries);
        //listViewCountries.setAdapter(countriesAdapter);
       /*
        recyclerViewSimpsons.OnItemTouchListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (sendMessage!=null){
                    sendMessage.sendData(position);
                }
            }
        });
*/
    }

    @Override
    public void onPersonSelected(int position) {//position is the index of selected item
        if (sendMessage!=null){
            sendMessage.sendData(position);
        }
    }

    public interface SendMessage {

        void sendData(int country_index);
        //void setToolbar();
        void sendToggle();
    }

    public void setSendMessage(SendMessage sendMessage){
        this.sendMessage = sendMessage;
    }
}
