package com.example.eatmeet.connections;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by umberto on 02/07/16.
 */
public class HttpRestClient {

    private static final AsyncHttpClient client = new AsyncHttpClient();
    private static PersistentCookieStore myCookieStore;

    private HttpRestClient() {

    }

    public static void get(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        if(requestParams==null) {
            requestParams = new RequestParams();
        }
        setTokenToHeaders(requestParams);
        client.get(url, requestParams, responseHandler);
        //saveTokenToCookie(responseHandler.getRequestHeaders());
    }

    public static void post(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        setTokenToHeaders(requestParams);
        Logger.getLogger(HttpRestClient.class.getName()).log(Level.WARNING, requestParams.toString()+"REQUEST");
        client.post(url, requestParams, responseHandler);
    }

    public static void post(String url, JSONObject requestParams, AsyncHttpResponseHandler responseHandler) {
        setTokenToHeaders(requestParams);
        StringEntity stringEntity;
        try {
            stringEntity = new StringEntity(requestParams.toString());
            stringEntity.setContentType("application/json");
            Logger.getLogger(HttpRestClient.class.getName()).log(Level.WARNING, requestParams.toString()+"REQUEST");
            client.post(null, url, stringEntity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(HttpRestClient.class.getName(), e.getMessage());
        }
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
        //saveTokenToCookie(responseHandler.getRequestHeaders());
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

    public static void setTokenToHeaders(JSONObject requestParams) {
        for(Cookie cookie : getCookieStore().getCookies()) {
            try {
                requestParams.put(cookie.getName(), cookie.getValue());
            } catch (JSONException e) {
                Logger.getLogger(HttpRestClient.class.getName(), e.getMessage());
            }
        }
    }

    public static void saveTokenToCookie(Header[] headers) {
        ArrayList<String> acceptedCookies = new ArrayList<>();
        //HttpRestClient.getCookieStore().clear();
        acceptedCookies.add("Set-Cookie");
        acceptedCookies.add("access-token");
        acceptedCookies.add("token-type");
        acceptedCookies.add("client");
        acceptedCookies.add("expiry");
        acceptedCookies.add("uid");
        //acceptedCookies.add("_eatameet_prod_session");

        System.out.println("COOKIES BEFORE: " + HttpRestClient.getCookieStore().getCookies());

        for( Header h : headers ) {
            BasicClientCookie cookie;
            if(acceptedCookies.contains(h.getName())) {
                cookie = new BasicClientCookie(h.getName(), h.getValue());
                HttpRestClient.getCookieStore().addCookie(cookie);
            }
        }

        System.out.println("COOKIES AFTER:" + HttpRestClient.getCookieStore().getCookies());
        System.out.println("COOKIES AFTER:" + HttpRestClient.getCookieStore().getCookies().size());
    }
}
