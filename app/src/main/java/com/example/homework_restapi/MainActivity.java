package com.example.homework_restapi;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.homework_restapi.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String tag_json_obj = "json_obj_req";

    //String url = "https://api.androidhive.info/volley/person_object.json";
    String url = "http://api.duckduckgo.com/?q=simpsons+characters&format=json";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                            StringBuilder sb = new StringBuilder();
                            JSONArray jsonArray = response.getJSONArray("RelatedTopics");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject character = jsonArray.getJSONObject(i);
                                String name = character.getString("Text");
                                name = name.substring(0, name.indexOf("-"));
                                sb.append(name + "\n");
                                //Log.i("mylog", "Character: " + name);
                            }
                            showMessage("characters", sb.toString());
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
    public void showMessage(String title, String Messager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messager);
        builder.show();
    }
}
