package com.example.alfredo.starwars;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listView;
    private StarWarsAdapter adapter;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lista);

        adapter = new StarWarsAdapter(this,R.layout.character_layout,new ArrayList<Personaje>());
        listView.setAdapter(adapter);
        mQueue = VolleySingleton.getInstance(this).getRequestQueue();

        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=1&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=2&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=3&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=4&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=5&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=6&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=7&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=8&format=json"),adapter);
        jsonStarWars(getStarWarsString("https://swapi.co/api/people/?page=9&format=json"),adapter);
        

    }


    private final String LOG_TAG = "WARS";

    private static char[] HEXCodes = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private void jsonStarWars(String url, final StarWarsAdapter adapter){
        adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Personaje p = new Personaje();
                        p.name = jsonObject.getString("name");
                        p.year = jsonObject.getString("birth_year");
                        p.icon = "https://zdoom.org/w/images/9/9b/ChibiStormtrooper.png";
                        adapter.add(p);
                    }
                    adapter.notifyDataSetChanged(); //actualiza la vista
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private String getStarWarsString(String url){
        //final String CHARACTER_BASE_URL ="https://swapi.co/api/people/?page=1&format=json";

        Uri builtUri;
        builtUri = Uri.parse(url).buildUpon().build();

        return builtUri.toString();
    }


}
