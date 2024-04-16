package com.example.imgallery;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIManager.fetchData(this, new APIManager.OnDataReceivedListener() {
            @Override
            public void onDataReceived(ArrayList<GalleryModel> data) {
                setContentView(R.layout.activity_main);
                GridView coursesGV = findViewById(R.id.gridView);
                ArrayList<GalleryModel> courseModelArrayList = new ArrayList<>(data);
                GalleryAdapter adapter = new GalleryAdapter(MainActivity.this, courseModelArrayList);
                coursesGV.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("APIManager", "Error: " + errorMessage);
            }
        });
    }
}