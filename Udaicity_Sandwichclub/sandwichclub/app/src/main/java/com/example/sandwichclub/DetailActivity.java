package com.example.sandwichclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandwichclub.Utils.JsonUtils;
import com.example.sandwichclub.model.Sandwich;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
RatingBar bar;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bar = findViewById(R.id.rate);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        try{
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];

            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display all Sandwich details
     */
    private void populateUI(Sandwich sandwich) {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView alsoNameDisplay = findViewById(R.id.also_known_tv);
        TextView ingredientsDisplay = findViewById(R.id.ingredients_tv);
        TextView descriptionDisplay = findViewById(R.id.description_tv);
        TextView originDisplay = findViewById(R.id.origin_tv);


        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        List<String> nameList = sandwich.getAlsoKnownAs();
        for(int i=0 ; i< nameList.size() ; i++){
            alsoNameDisplay.append(nameList.get(i) + ". ");
        }

        List<String> ingredientsList = sandwich.getIngredients();
        for(int i=0 ; i< ingredientsList.size() ; i++){
            ingredientsDisplay.append(ingredientsList.get(i) + ". ");
        }

        originDisplay.append(sandwich.getPlaceOfOrigin());
        descriptionDisplay.append(sandwich.getDescription());


        /**
         * Rating Bar
         */
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(DetailActivity.this, ""+v, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
