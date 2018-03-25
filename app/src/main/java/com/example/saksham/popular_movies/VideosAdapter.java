package com.example.saksham.popular_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Saksham on 06-02-2018.
 */

public class VideosAdapter  extends ArrayAdapter<Videos> {

    private Context context;
    public VideosAdapter(Context context, List<Videos> movie) {
        super(context, 0, movie);
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.video_list_item, parent, false);
        }


        Videos currentreview = getItem(position);


        TextView name=(TextView)listItemView.findViewById(R.id.nameid);
        String nametext=currentreview.getName();
        name.setText(nametext);
        return listItemView;
    }
}
