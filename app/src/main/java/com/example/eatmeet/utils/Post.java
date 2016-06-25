package com.example.eatmeet.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sofia on 19/06/2016.
 */
public class Post extends AsyncTask<Object, Void, String> {

    public Post(){}

    @Override
    protected String doInBackground(Object... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String myJsonStr;
        try {
            String myUrl = (String) params[0];
            /*
            HashMap<String, String> postDataParams = (HashMap<String, String>) params[1];
            JSONObject postDataJson = null;
            Iterator it = postDataParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                try {
                    postDataJson.put((String) pair.getKey(),pair.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                it.remove();
            }
            */
            JSONObject postDataJson = (JSONObject) params[1];
            // Construct the URL for the query
            URL url = new URL(myUrl);

            // Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            CookieHandler.setDefault(CookiesUtil.getCookieManager());

            urlConnection.connect();

            OutputStream os = urlConnection.getOutputStream();
            System.out.println("AAAAAAAAAAApostDataJson: " + postDataJson);
            os.write(postDataJson.toString().getBytes("UTF-8"));
            /*
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            */
            os.close();

            CookiesUtil.setAuthCookies(urlConnection);

            InputStream inputStream;
            int responseCode=urlConnection.getResponseCode();

            switch (responseCode) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    inputStream = urlConnection.getErrorStream();
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    inputStream = urlConnection.getErrorStream();
                    break;
                default:
                    inputStream = urlConnection.getInputStream();
            }


            // Read the input stream into a String
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            myJsonStr = buffer.toString();
            return myJsonStr;
        } catch (IOException e) {
            Log.e("Connection", "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Connection", "Error closing stream", e);
                }
            }
        }
    }
}