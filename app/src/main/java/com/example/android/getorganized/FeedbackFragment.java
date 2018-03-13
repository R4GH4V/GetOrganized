package com.example.android.getorganized;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;


public class FeedbackFragment extends Fragment {

    private final static String DEBUG_TAG = "FeedbackFragment";


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.feedback);
        return inflater.inflate(R.layout.fragment_feedback, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RatingBar ratingRatingBar =  getView().findViewById(R.id.rating_rating_bar);
        Button submitButton =  getView().findViewById(R.id.submit_button);
        final TextView feedback_tv = getView().findViewById(R.id.tv_feedback);
        final Button yes_btn =  getView().findViewById(R.id.btn_yes);
        final Button no_btn =  getView().findViewById(R.id.btn_no);
        //final Integer score = Math.round(ratingRatingBar.getRating());
        //Log.d(DEBUG_TAG, "score: " + score);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer score = Math.round(ratingRatingBar.getRating());
                if (score >= 4) {
                    feedback_tv.setText(R.string.feedbackplaystore);
                    yes_btn.setVisibility(View.VISIBLE);
                    yes_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_LONG).show();
                            // TODO: set yes_btn.setOnCluckListern to playstore page
                        }
                    });
                    no_btn.setVisibility(View.VISIBLE);
                    no_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(),"No, maybe later.", Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    feedback_tv.setText(R.string.feedbackemail);
                    yes_btn.setVisibility(View.VISIBLE);
                    no_btn.setVisibility(View.VISIBLE);
                    yes_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setData(Uri.parse("mailto:"));
                            String[] to = {"r2bhandari@scu.edu", "xwu2@scu.edu", "nsohagiya@scu.edu"};
                            intent.putExtra(Intent.EXTRA_EMAIL, to);
                            intent.putExtra(Intent.EXTRA_SUBJECT, "App Feedback");
                            intent.setType("message/rfc822");
                            Intent chooser = Intent.createChooser(intent, "Send Email");
                            startActivity(chooser);
                        }
                    });
                    no_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "No, maybe later.", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
}