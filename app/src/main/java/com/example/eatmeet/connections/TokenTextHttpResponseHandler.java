package com.example.eatmeet.connections;

import com.example.eatmeet.entities.Event;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 02/07/16.
 */
public abstract class TokenTextHttpResponseHandler extends TextHttpResponseHandler {

    @Override
    public final void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        this.onFailureAction(statusCode, headers, responseString, throwable);
    }

    @Override
    public final void onSuccess(int statusCode, Header[] headers, String responseString) {
        HttpRestClient.saveTokenToCookie(headers);
        this.onSuccessAction(statusCode, headers, responseString);
    }

    public abstract void onFailureAction(int statusCode, Header[] headers, String responseString, Throwable throwable);

    public abstract void onSuccessAction(int statusCode, Header[] headers, String responseString);

}
