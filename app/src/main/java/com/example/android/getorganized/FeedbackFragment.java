package com.example.android.getorganized;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class FeedbackFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RatingBar ratingRatingBar = (RatingBar) getView().findViewById(R.id.rating_rating_bar);
        Button submitButton = (Button) getView().findViewById(R.id.submit_button);
        final TextView ratingDisplayTextView = (TextView) getView().findViewById(R.id.rating_display_text_View);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDisplayTextView.setText(getResources().getString(R.string.yourrating)+ ratingRatingBar.getRating());
            }
        });
    }
}
