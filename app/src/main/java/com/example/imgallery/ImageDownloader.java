package com.example.imgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ImageDownloader {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final MemoryCache memoryCache = new MemoryCache();
    private DiskCache diskCache;

    public ImageDownloader(Context context) {
        diskCache = new DiskCache(context);
    }


    public void downloadImage(final String imageUrl, final ImageView imageView) {
        final Bitmap cachedBitmapDisk = diskCache.getBitmapFromDiskCache(imageUrl);
        if (cachedBitmapDisk != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(cachedBitmapDisk);
                }
            });
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = downloadBitmapFromUrl(imageUrl);
                if (bitmap != null) {
                    diskCache.addBitmapToDiskCache(imageUrl, bitmap);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        });
    }

    private Bitmap downloadBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}