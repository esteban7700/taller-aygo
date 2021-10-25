package co.edu.escuelaing.balancer.response;

import co.edu.escuelaing.balancer.model.LogData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogResponse {
    private List<LogData> logs;
}
