package com.jook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;

import com.R;

public class Recommendation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
    }

    public void AddRecommendation(View view) {

        String Text=(((EditText)findViewById(R.id.recommendation_content)).getText()).toString();
        int numStars=((RatingBar)findViewById(R.id.get_stars)).getNumStars();
        try
    }
}
