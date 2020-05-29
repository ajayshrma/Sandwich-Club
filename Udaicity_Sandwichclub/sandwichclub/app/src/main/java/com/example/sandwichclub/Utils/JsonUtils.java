package com.example.sandwichclub.Utils;

import android.media.Image;
import android.util.Log;

import com.example.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String Title = JsonUtils.class.getSimpleName();

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String AKA = "alsoKnownAs";
    private static final String DESCRIPTION = "description";
    private static final String INGREDIENTS = "ingredients";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String IMAGE_SRC = "image";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Log.d(Title, "json = "+json);
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJson = new JSONObject(json);

        JSONObject name = sandwichJson.getJSONObject(NAME);
        String mainName = name.getString(MAIN_NAME);
        sandwich.setMainName(mainName);

        String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN);
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        String description = sandwichJson.getString(DESCRIPTION);
        sandwich.setDescription(description);

        String imgSrc = sandwichJson.getString(IMAGE_SRC);
        sandwich.setImage(imgSrc);

        JSONArray knownAsArray = name.getJSONArray(AKA);
        List<String> alsoKnownAs = new ArrayList<>();
        for(int i= 0 ; i< knownAsArray.length() ; i++){
            alsoKnownAs.add(knownAsArray.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        JSONArray ingredientsArray = sandwichJson.getJSONArray(INGREDIENTS);
        List<String> ingredientsList = new ArrayList<>();
        for(int i=0 ; i< ingredientsArray.length() ; i++){
            ingredientsList.add(ingredientsArray.getString(i));
        }
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }

}
