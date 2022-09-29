package antifraud.model.User.Request;

import antifraud.model.User.UserRoles;
import lombok.Data;

@Data
public class RoleRequest {
    private String username;
    private UserRoles role;
}
