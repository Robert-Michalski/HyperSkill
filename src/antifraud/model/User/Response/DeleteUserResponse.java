package antifraud.model.User.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteUserResponse {
    private String username;
    private String status;
}
