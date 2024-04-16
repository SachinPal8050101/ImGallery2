package com.example.imgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DiskCache {
    private final File cacheDir;

    public DiskCache(Context context) {
        cacheDir = context.getCacheDir();
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        String filename = hashKeyForDisk(key);
        File imageFile = new File(cacheDir, filename);
        if (imageFile.exists()) {
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                return BitmapFactory.decodeStream(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String hashKeyForDisk(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(key.getBytes());
            byte[] digestBytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digestBytes) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(key.hashCode());
        }
    }

    public void addBitmapToDiskCache(String key, Bitmap bitmap) {
        String filename = hashKeyForDisk(key);
        File imageFile = new File(cacheDir, filename);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}