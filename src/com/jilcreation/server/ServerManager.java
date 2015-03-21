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
}
