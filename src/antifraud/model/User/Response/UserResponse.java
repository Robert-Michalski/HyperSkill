package antifraud.model.User.Response;

import antifraud.model.User.User;
import antifraud.model.User.UserRoles;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserResponse {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String username;
    @NonNull
    private UserRoles role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
