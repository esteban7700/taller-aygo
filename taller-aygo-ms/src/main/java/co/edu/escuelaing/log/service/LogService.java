package co.edu.escuelaing.log.service;

import co.edu.escuelaing.log.model.LogData;
import co.edu.escuelaing.log.repository.LogRepository;
import co.edu.escuelaing.log.request.LogRequest;
import co.edu.escuelaing.log.response.LogResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class LogService {

    public static LogResponse saveAndGetLast(LogRequest request) {

        LogData logData = new LogData(request.getLog(), LocalDateTime.now(ZoneId.of("America/Bogota")));
        List<LogData> response = LogRepository.saveAndGetLast(logData);
        return new LogResponse(response);
    }

    public static LogResponse getLast() {
        List<LogData> response = LogRepository.getLast();
        return new LogResponse(response);
    }
}
