package one.challenge.helper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import one.challenge.Logger;

public class Api {
    public HttpClient client;
    public Api() {
        client = HttpClient.newHttpClient();
    }
    public<T> T GET(String url, Class<T> responseType) {
        URI uri = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
            .GET().uri(uri).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int code = response.statusCode();
            if (code == 200) {
                Gson gson = new Gson();
                T result = gson.fromJson(response.body(), responseType);
                return result;
            } else {
                Logger.log(new String[] {
                    "api-rs: Server response with code " + code,
                    "api-rs: " + response.body()
                });
                return null;
            }
        } catch (IOException | InterruptedException e) {
            Logger.log("Api query failed :c");
            return null;
        }
    }
}
