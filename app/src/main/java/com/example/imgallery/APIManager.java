package com.example.imgallery;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIManager {

    public interface OnDataReceivedListener {
        void onDataReceived(ArrayList<GalleryModel> data);
        void onError(String errorMessage);
    }

    public static void fetchData(Context context, OnDataReceivedListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.unsplash.com/photos/?client_id=Ln5QbESgSQjRp-e8ad-6686BCuzN9ClF9OhTVyw3450&per_page=100";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<GalleryModel> dataList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject photo = response.getJSONObject(i);
                                JSONObject urls = photo.getJSONObject("urls");
                                String rawUrl = urls.getString("small_s3");
                                dataList.add(new GalleryModel("Image " + i, rawUrl));
                            }
                            listener.onDataReceived(dataList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Volley Error: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }
}