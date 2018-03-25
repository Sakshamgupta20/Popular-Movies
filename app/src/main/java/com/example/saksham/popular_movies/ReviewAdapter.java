package com.example.saksham.popular_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Saksham on 06-02-2018.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    private Context context;
    public ReviewAdapter(Context context, List<Review> movie) {
        super(context, 0, movie);
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_list_item, parent, false);
        }


        Review currentreview = getItem(position);


        TextView author=(TextView)listItemView.findViewById(R.id.reviewauthor);
        String authortext=currentreview.getAuthor();
        author.setText(authortext);

        TextView review=(TextView)listItemView.findViewById(R.id.reviewcontent);
        String reviewtext=currentreview.getReview();
        review.setText(reviewtext);


        return listItemView;
    }
}
