package com.jook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.AndroidSuperApp;
import com.R;
import com.backend.entities.Recommendation;

public class RecommendationActivity extends AppCompatActivity {
    public static final String KEY_BOOK_ID = "bookID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
    }

    public void AddRecommendation(View view) {

        Intent intent=getIntent();
        String BookID=intent.getStringExtra(KEY_BOOK_ID);
        String Text=(((EditText)findViewById(R.id.recommendation_content)).getText()).toString();
        int numStars=((RatingBar)findViewById(R.id.get_stars)).getNumStars();

        try{
            Recommendation recommendation=new Recommendation(AndroidSuperApp.CurrAppUser.getID(),BookID,numStars,Text);
            AndroidSuperApp.BL.AddRecommendation(recommendation);
            Toast.makeText(RecommendationActivity.this, "המלצה הוספה בהצלחה", Toast.LENGTH_SHORT).show();
            finish();

        }
        catch (Exception ex){
            Toast.makeText(RecommendationActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
