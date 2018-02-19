package com.example.homework_restapi;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homework_restapi.R;
import com.example.homework_restapi.adapter.MyAdapter;
import com.example.homework_restapi.app.AppController;
import com.example.homework_restapi.model.Simpsons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SimpsonsFragment.SendMessage {
    String tag_json_obj = "json_obj_req";

    //String url = "https://api.androidhive.info/volley/person_object.json";
    String url = "http://api.duckduckgo.com/?q=simpsons+characters&format=json";
    ProgressDialog pDialog;
    //List<Simpsons> mySimpsonsList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //Toolbar mActionBarToolbar;

    int selectedSimpsonIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mActionBarToolbar = findViewById(R.id.toolbar);

        fragmentManager = getSupportFragmentManager();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        //StringRequest stringRequest = new StringRequest(request.M);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(AppController.TAG, response.toString());
                        //Log.i("mylog", response.toString());
                        //Toast.makeText(MainActivity.this, "get res" + response.toString(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                        //showMessage("json obj", response.toString());
                        try {
                            Log.i("mylog", "get response");
                            //StringBuilder sb = new StringBuilder();
                            JSONArray jsonArray = response.getJSONArray("RelatedTopics");
                            List<Simpsons> mySimpsonsList = new ArrayList<>();
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject character = jsonArray.getJSONObject(i);
                                String name = character.getString("Text");
                                String description;
                                String image = character.getJSONObject("Icon").getString("URL");
                                //Log.i("mylog", "obj i = " + i);
                                description = name.substring(name.indexOf("-") + 2);
                                name = name.substring(0, name.indexOf("-"));
                                //sb.append(name + "\n");
                                Log.i("mylog", "Character: " + name);

                                Simpsons simpson = new Simpsons(name, description, image);
                                mySimpsonsList.add(simpson);
                            }
                            //Log.i("mylog", "get json objs");
                            //showMessage("characters", sb.toString());
                            Simpsons.simpsons = mySimpsonsList;

                            addSimpsonsFragment();////!
                            if (findViewById(R.id.activity_main_large) != null) {
                                addDescriptionFragmentLarge(-1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Log.i("mylog", "Error: " + error.getMessage());
                // hide the progress dialog
                //pDialog.hide();
            }
        });

// Adding request to request queue
        if (AppController.getInstance() == null) {
            Log.i("mylog", "Error: null instance");
        } else {
            Log.i("mylog", "instance");
        }
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        //RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //requestQueue.add(jsonObjReq);
    }
    private void addSimpsonsFragment(){
        //mActionBarToolbar.setTitle("Simpson Characters");
        fragmentTransaction=fragmentManager.beginTransaction();

        SimpsonsFragment simpsonsFragment = new SimpsonsFragment();
        simpsonsFragment.setSendMessage(MainActivity.this);

        fragmentTransaction.replace(R.id.fragmentContainer, simpsonsFragment);//in CountriesFragment import android.support.v4.app.Fragment;
        fragmentTransaction.commit();
        Log.i("mylog", "add list");
    }
    private void addDescriptionFragment(int person_index){
        //mActionBarToolbar.setTitle(Simpsons.simpsons.get(person_index).getName());//set toolbar
        fragmentTransaction=fragmentManager.beginTransaction();

        DescriptionFragment descriptionFragment=new DescriptionFragment();


        Bundle bundle=new Bundle();
        bundle.putInt("simpson_index",person_index);
        descriptionFragment.setArguments(bundle);
        bundle.putString("screen_mode", "phone");

        fragmentTransaction.replace(R.id.fragmentContainer,descriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void addDescriptionFragmentLarge(int person_index){
        //mActionBarToolbar.setTitle(Simpsons.simpsons.get(person_index).getName());//set toolbar
        fragmentTransaction=fragmentManager.beginTransaction();

        DescriptionFragment descriptionFragment=new DescriptionFragment();


        Bundle bundle=new Bundle();
        bundle.putInt("simpson_index",person_index);
        descriptionFragment.setArguments(bundle);
        descriptionFragment.setArguments(bundle);
        bundle.putString("screen_mode", "tablet");

        fragmentTransaction.replace(R.id.fragmentContainer2,descriptionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void showMessage(String title, String Messager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messager);
        builder.show();
    }

    @Override
    public void sendData(int person_index) {
        //Toast.makeText(this, "show description", Toast.LENGTH_SHORT).show();
        selectedSimpsonIndex = person_index;
        Toast.makeText(MainActivity.this, "simpson #" + person_index, Toast.LENGTH_SHORT).show();
        //addDescriptionFragment(person_index);

        if(findViewById(R.id.activity_main_portrait)!= null) {//portrait
            addDescriptionFragment(person_index);
        } else if (findViewById(R.id.activity_main_large)!=null) {
            addDescriptionFragmentLarge(person_index);
        }

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("mylog", "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("mylog", "protrait");
        }
    }
/*
    @Override
    public void setToolbar() {
        mActionBarToolbar.setTitle("Simpson Characters");
    }*/
}
