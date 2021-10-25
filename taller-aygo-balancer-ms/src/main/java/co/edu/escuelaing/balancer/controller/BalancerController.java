package co.edu.escuelaing.balancer.controller;

import co.edu.escuelaing.balancer.request.LogRequest;
import co.edu.escuelaing.balancer.response.LogResponse;
import co.edu.escuelaing.balancer.service.RoundRobinBalancerService;
import com.google.gson.Gson;

import static spark.Spark.*;

public class BalancerController {

    public static void main(String... args){

        port(getPort());

        allowCors();

        get("health", (req,res) -> "Status Ok");

        get("balancer/log", (req, res) -> {
            res.type("application/json");

            return new Gson().toJson(RoundRobinBalancerService.getLast());
        });

        post("balancer/log", (req, res) -> {
            res.type("application/json");

            LogRequest request = new Gson().fromJson(req.body(), LogRequest.class);
            LogResponse response = RoundRobinBalancerService.saveAndGetLast(request);
            return new Gson().toJson(response);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8901;
    }

    private static void allowCors() {
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }
}
