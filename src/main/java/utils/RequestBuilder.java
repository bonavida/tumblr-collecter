package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class that represents an HTTP Request
 */
public class RequestBuilder {

    public RequestBuilder() {}

    /**
     * HTTP GET Request
     * @param myUrl - URL to make the HTTP request
     * @return the HTTP response in JSON format
     * @throws Exception
     */
    public JsonElement get(String myUrl) throws Exception {

        try {
            URL urlObj = new URL(myUrl);
            HttpURLConnection request = (HttpURLConnection) urlObj.openConnection();

            // Optional - default method is already GET
            request.setRequestMethod("GET");

            request.connect();

            InputStream is = request.getInputStream();
            JsonElement jsonResponse = new JsonParser().parse(new InputStreamReader(is));

            request.disconnect();

            return jsonResponse;

        } catch (Exception ex) {
            return null;
        }
    }
}
