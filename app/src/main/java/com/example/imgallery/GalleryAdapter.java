package com.example.imgallery;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GalleryAdapter extends ArrayAdapter<GalleryModel> {
    private ImageDownloader imageDownloader;
    public GalleryAdapter(@NonNull Context context, ArrayList<GalleryModel> imageData) {
        super(context, 0, imageData);
        imageDownloader = new ImageDownloader(context);
    }



    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        GalleryModel galleryModel = getItem(position);
        ImageView image = listitemView.findViewById(R.id.imageView);
        imageDownloader.downloadImage(galleryModel.getImageId(), image);
        return listitemView;
    }



}
