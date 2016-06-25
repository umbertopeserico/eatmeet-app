package com.example.eatmeet.utils;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by umberto on 25/06/16.
 */
public class CookiesUtil {
    private static CookieManager cookieManager;

    public static void setAuthCookies(HttpURLConnection urlConnection) {
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();

        List<String> accessToken = headerFields.get("access-token");
        System.out.println(accessToken);
        List<String> tokenType = headerFields.get("token-type");
        List<String> client = headerFields.get("client");
        List<String> expiry = headerFields.get("expiry");
        List<String> uid = headerFields.get("uid");
        List<String> cookiesHeader = headerFields.get("Set-Cookie");

        if(cookiesHeader != null) {
            for (String cookie : accessToken) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse("access-token="+cookie).get(0));
            }

            for (String cookie : tokenType) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse("token-type="+cookie).get(0));
            }

            for (String cookie : client) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse("client="+cookie).get(0));
            }

            for (String cookie : expiry) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse("expiry="+cookie).get(0));
            }

            for (String cookie : uid) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse("uid="+cookie).get(0));
            }

            for (String cookie : cookiesHeader) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    public static void setCookieOnARequest(HttpURLConnection urlConnection) {
        if(cookieManager.getCookieStore().getCookies().size() > 0) {
            System.out.println(cookieManager.getCookieStore().getCookies());

            urlConnection.setRequestProperty("access-token", cookieManager.getCookieStore().getCookies().get(0).getValue());
            urlConnection.setRequestProperty("token-type", cookieManager.getCookieStore().getCookies().get(1).getValue());
            urlConnection.setRequestProperty("client", cookieManager.getCookieStore().getCookies().get(2).getValue());
            urlConnection.setRequestProperty("expiry", cookieManager.getCookieStore().getCookies().get(3).getValue());
            urlConnection.setRequestProperty("uid", cookieManager.getCookieStore().getCookies().get(4).getValue());
        }
    }

    public static CookieManager getCookieManager() {
        if (cookieManager == null) {
            return cookieManager = new CookieManager();
        }
        else {
            return cookieManager;
        }

    }
}
