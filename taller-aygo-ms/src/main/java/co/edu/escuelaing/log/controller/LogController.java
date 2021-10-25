package co.edu.escuelaing.log.controller;

import co.edu.escuelaing.log.request.LogRequest;
import co.edu.escuelaing.log.response.LogResponse;
import co.edu.escuelaing.log.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

public class LogController {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String... args){
        port(getPort());

        get("health", (req,res) -> "Status Ok");

        get("log", (req, res) -> {
            res.type("application/json");

            return mapper.writeValueAsString(LogService.getLast());
        });

        post("log", (req, res) -> {
            res.type("application/json");

            LogRequest request = new Gson().fromJson(req.body(), LogRequest.class);
            LogResponse response = LogService.saveAndGetLast(request);
            return mapper.writeValueAsString(response);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
