package com.example.eatmeet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by sofia on 18/06/2016.
 */
public class Images extends AsyncTask<String, Void, Bitmap> {

    public Images(){}

    @Override
    protected void onPostExecute(Bitmap result) {

    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String icon = params[0];

        Bitmap myBitmap = null;
        try {
            java.net.URL url = new java.net.URL(icon);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myBitmap;
    }
}
