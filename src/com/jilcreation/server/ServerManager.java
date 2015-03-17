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
}
