package building.sma.inventory.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errors;

}
