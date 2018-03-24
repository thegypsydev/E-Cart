package com.example.ashu.wendor;

import android.app.Activity;
import android.content.ContentValues;

import com.example.ashu.wendor.Images.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ashu on 19/3/18.
 */

public class JsonData {


    public static void getJsonData(String JsonFile, Activity activity) {

        try {
            if (JsonFile != null) {


                JSONObject root = new JSONObject(JsonFile);
                JSONArray itemsArray = root.optJSONArray("items");
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject eachItem = itemsArray.optJSONObject(i);
                    int itemId = i + 1;
                    String name = eachItem.getString("name");
                    int price = eachItem.getInt("price");
                    int totUnits = eachItem.getInt("tot_units");
                    int leftUnits = eachItem.getInt("left_units");
                    String imageUrl = eachItem.getString("image_url");

                    ContentValues cv = new ContentValues();
                    cv.put(WendorContentProvider.itemId, itemId);
                    cv.put(WendorContentProvider.name, name);
                    cv.put(WendorContentProvider.price, price);
                    cv.put(WendorContentProvider.totUnits, totUnits);
                    cv.put(WendorContentProvider.leftUnits, leftUnits);
                    cv.put(WendorContentProvider.imageUrl, imageUrl);

                    //Downloading And Saving Image....
                    ImageLoader imageLoader = new ImageLoader(SplashActivity.appContext);
                    imageLoader.downloadSaveImageFromUrl(imageUrl, name);
                    String imagePath = imageLoader.getFileLocation(name);


                    cv.put(WendorContentProvider.imagePath, imagePath);

                    activity.getContentResolver().insert(
                            WendorContentProvider.CONTENT_URI, cv);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
