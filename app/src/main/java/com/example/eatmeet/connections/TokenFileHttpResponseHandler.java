package com.example.eatmeet.connections;

import android.content.Context;

import com.loopj.android.http.*;

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by umberto on 02/07/16.
 */
public abstract class TokenFileHttpResponseHandler extends FileAsyncHttpResponseHandler {

    public TokenFileHttpResponseHandler(File file) {
        super(file);
    }

    public TokenFileHttpResponseHandler(File file, boolean append) {
        super(file, append);
    }

    public TokenFileHttpResponseHandler(File file, boolean append, boolean renameTargetFileIfExists) {
        super(file, append, renameTargetFileIfExists);
    }

    public TokenFileHttpResponseHandler(File file, boolean append, boolean renameTargetFileIfExists, boolean usePoolThread) {
        super(file, append, renameTargetFileIfExists, usePoolThread);
    }

    public TokenFileHttpResponseHandler(Context context) {
        super(context);
    }

    @Override
    public final void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
        this.onFailureAction(statusCode, headers, throwable, file);
    }

    @Override
    public final void onSuccess(int statusCode, Header[] headers, File file) {
        HttpRestClient.saveTokenToCookie(headers);
        this.onSuccessAction(statusCode, headers, file);
    }

    public abstract void onFailureAction(int statusCode, Header[] headers, Throwable throwable, File file);

    public abstract void onSuccessAction(int statusCode, Header[] headers, File file);
}
