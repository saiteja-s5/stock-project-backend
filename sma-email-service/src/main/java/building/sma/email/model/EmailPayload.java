package building.sma.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailPayload {

    String from;
    String to;
    String subject;
    String body;
    String attachmentName;
    byte[] attachmentData;

}
