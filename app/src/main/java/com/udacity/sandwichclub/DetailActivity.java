package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageIv = findViewById(R.id.image_iv);

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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Populate the activity_detail.xml with the Sandwich state.
        populateUI(sandwich);

        // Attach Sandwich image to ImageView in activity_detail.xml.
        Picasso.with(this).load(sandwich.getImage()).into(imageIv);

        // Set title of activity_detail.xml to the Sandwich's mainName.
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // TODO (3) populate the UI with the sandwich object.
        TextView mNameTextView = findViewById(R.id.name_tv);
        TextView mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView mOriginTextView = findViewById(R.id.origin_tv);
        TextView mDescriptionTextView = findViewById(R.id.description_tv);
        TextView mIngredientsTextView = findViewById(R.id.ingredients_tv);

        mNameTextView.setText(sandwich.getMainName());
        List<String> aliasList = sandwich.getAlsoKnownAs();
        for (int i = 0; i < aliasList.size(); i++) {
            if (i == aliasList.size() - 1) {
                mAlsoKnownAsTextView.append(aliasList.get(i));
            } else {
                mAlsoKnownAsTextView.append(aliasList.get(i) + ", ");
            }
        }
        mOriginTextView.append(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.append(sandwich.getDescription());
        List<String> ingredientsList = sandwich.getIngredients();
        for (int i = 0; i < ingredientsList.size(); i++) {
            if (i == ingredientsList.size() - 1) {
                mIngredientsTextView.append(ingredientsList.get(i));
            } else {
                mIngredientsTextView.append(ingredientsList.get(i) + ", ");
            }
        }
    }
}
