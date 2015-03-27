package com.jilcreation.server;

import com.jilcreation.server.http.AsyncHttpClient;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import com.jilcreation.server.http.RequestParams;

public class ServerManager {
	public static void getAllDeals(AsyncHttpResponseHandler handler) {
        String url = APIManager.CMD_GETDEALS;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getSelectDeal(AsyncHttpResponseHandler handler, long dealId) {
        String url = APIManager.CMD_SELECTDEAL + "?id=" + dealId;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getAllHibitors(AsyncHttpResponseHandler handler) {
        String url = APIManager.CMD_GETEXHIBITORS;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void getProgrammes(int type, AsyncHttpResponseHandler handler) {
        String url = APIManager.CMD_GETPROGRAMMES;

        switch (type) {
            case 1:
                url = url + "?category=" + "main%20stage";
                break;
            case 2:
                url = url + "?category=" + "second%20stage";
                break;
            case 3:
                url = url + "?category=" + "third%20stage";
                break;
        }

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void addUser(AsyncHttpResponseHandler handler) {
        String url = APIManager.CMD_ADDUSER;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void updatePref(AsyncHttpResponseHandler handler, long userId, int a, int b, int c, int d) {
        String url = APIManager.CMD_UPDATEPREF + "?uid=" + userId + "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void updateDuration(AsyncHttpResponseHandler handler, long userId, long dealId, long duration) {
        String url = APIManager.CMD_UPDATEDURATION + "?uid=" + userId + "&did=" + dealId + "&duration=" + duration;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }

    public static void searchBy(AsyncHttpResponseHandler handler, String query) {
        String url = APIManager.CMD_SEARCHBY + "?x=" + query;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(APIManager.connectTimeout);
            client.post(url, null, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            if (handler != null)
                handler.onFailure(null, ex.getMessage());
        }
    }
}
