package antifraud.service.UserService;

import antifraud.model.User.Request.AccessRequest;
import antifraud.model.User.Response.AccessResponse;
import antifraud.model.User.Response.DeleteUserResponse;
import antifraud.model.User.Request.RoleRequest;
import antifraud.model.User.User;
import antifraud.model.User.Response.UserResponse;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<UserResponse> showUsers();

    DeleteUserResponse deleteUser(String username);

    UserResponse changeRole(RoleRequest request);

    AccessResponse changeAccess(AccessRequest request);
}
