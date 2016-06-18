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
public class Images extends AsyncTask<String, Void, String> {

    public Images(){}

    @Override
    protected void onPostExecute(String result) {
        Log.d("My tag",result);
    }

    @Override
    protected String doInBackground(String... params) {

        String icon = params[0];
        String name = params[1];

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

        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        File file = new File(path, name+".jpg"); // the File to save to
        try {
            fOut = new FileOutputStream(file);
        } catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        try{fOut.flush();}catch(java.io.IOException e){e.printStackTrace();}
        try{fOut.close();}catch(java.io.IOException e){e.printStackTrace();}// do not forget to close the stream
        //MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());

        return Environment.getExternalStorageDirectory().toString() + name +".jpg";
    }
}
