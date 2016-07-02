package com.example.eatmeet.connections;

import android.content.Context;

import com.example.eatmeet.utils.Configs;
import com.loopj.android.http.*;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by umberto on 02/07/16.
 */
public class BackendRest {

    private static final AsyncHttpClient client = new AsyncHttpClient();
    private static PersistentCookieStore myCookieStore;

    private BackendRest() {

    }

    public static void get(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        if(requestParams==null) {
            requestParams = new RequestParams();
        }
        setTokenToHeaders(requestParams);
        client.get(url, requestParams, responseHandler);
    }

    public static void post(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        if(requestParams==null) {
            requestParams = new RequestParams();
        }
        setTokenToHeaders(requestParams);
        client.post(url, requestParams, responseHandler);
    }

    public static void put(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        if(requestParams==null) {
            requestParams = new RequestParams();
        }
        setTokenToHeaders(requestParams);
        client.put(url, requestParams, responseHandler);
    }

    public static void delete(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        if(requestParams==null) {
            requestParams = new RequestParams();
        }
        setTokenToHeaders(requestParams);
        client.delete(url, requestParams, responseHandler);
    }

    public static void setConfigurations(Context context) {
        myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
    }

    public static PersistentCookieStore getCookieStore() {
        return myCookieStore;
    }

    public static void setTokenToHeaders(RequestParams requestParams) {
        for(Cookie cookie : getCookieStore().getCookies()) {
            requestParams.put(cookie.getName(), cookie.getValue());
        }
    }

    public static void saveTokenToCookie(Header[] headers) {
        BackendRest.getCookieStore().clear();

        ArrayList<String> acceptedCookies = new ArrayList<>();
        acceptedCookies.add("Set-Cookie");
        acceptedCookies.add("access-token");
        acceptedCookies.add("token-type");
        acceptedCookies.add("client");
        acceptedCookies.add("expiry");
        acceptedCookies.add("uid");

        for( Header h : headers ) {
            BasicClientCookie cookie;
            if(acceptedCookies.contains(h.getName())) {
                cookie = new BasicClientCookie(h.getName(), h.getValue());
                BackendRest.getCookieStore().addCookie(cookie);
            }
        }
    }
}
