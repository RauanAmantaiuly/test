package restassuredKP2;

import lombok.Data;

@Data
public class ErrorResponse {
    private String field;
    private String message;
}
