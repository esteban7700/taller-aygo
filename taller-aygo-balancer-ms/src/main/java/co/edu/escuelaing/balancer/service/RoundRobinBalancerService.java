package co.edu.escuelaing.balancer.service;

import co.edu.escuelaing.balancer.request.LogRequest;
import co.edu.escuelaing.balancer.response.LogResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinBalancerService {

    private static final AtomicInteger ind = new AtomicInteger(0);
    private static final int[] servers = new int[]{0, 1, 2};
    private static final String[] hostServers = System.getenv("LOG_HOSTS").split(",");
    private static OkHttpClient httpClient;

    public static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        return httpClient;
    }

    public static int getServer() {
        return servers[ind.getAndAccumulate(servers.length, (cur, n) -> cur >= n - 1 ? 0 : cur + 1)];
    }

    public static LogResponse saveAndGetLast(LogRequest logRequest) {

        String host = hostServers[getServer()];

        Request request = new Request.Builder()
                        .url(host + "/log")
                        .post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(logRequest)))
                        .build();

        String response = null;

        try {
            response = getHttpClient().newCall(request).execute().body().string();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("--POST --Host: " + request.urlString() + " --Request: " + new Gson().toJson(request) + " --Response: " + response);

        return new Gson().fromJson(response, LogResponse.class);
    }

    public static LogResponse getLast() {

        String host = hostServers[getServer()];

        Request request = new Request.Builder()
                .url(host + "/log")
                .build();

        String response = null;

        try {
            response = getHttpClient().newCall(request).execute().body().string();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("GET --Host: " + request.urlString() + " --Request: " + new Gson().toJson(request) + " --Response: " + response);

        return new Gson().fromJson(response, LogResponse.class);
    }
}
