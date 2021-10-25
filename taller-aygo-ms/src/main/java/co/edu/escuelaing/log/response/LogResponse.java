package co.edu.escuelaing.log.response;

import co.edu.escuelaing.log.model.LogData;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Data
public class LogResponse {
    private List<LogData> logs;
}
