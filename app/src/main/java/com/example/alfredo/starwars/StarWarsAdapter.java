package com.example.alfredo.starwars;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Alfredo on 16/02/2018.
 */

public class StarWarsAdapter extends ArrayAdapter<Personaje> {

    private Context context;

    public StarWarsAdapter(Context context, int resource, List<Personaje> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Personaje personaje = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.character_layout,parent, false);
        }

        TextView nombre = (TextView)convertView.findViewById(R.id.nombre);
        TextView año = (TextView)convertView.findViewById(R.id.año);
        NetworkImageView imagen = (NetworkImageView)convertView.findViewById(R.id.imagen);

        nombre.setText(personaje.name);
        año.setText(personaje.year);

        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
        imagen.setImageUrl(personaje.icon,imageLoader);



        return convertView;
    }
}
