package antifraud.controller;

import antifraud.model.User.Request.AccessRequest;
import antifraud.model.User.Response.AccessResponse;
import antifraud.model.User.Response.DeleteUserResponse;
import antifraud.model.User.Request.RoleRequest;
import antifraud.model.User.User;
import antifraud.model.User.Response.UserResponse;
import antifraud.service.UserService.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody User user) {
        User user1 = userService.saveUser(user);
        return new UserResponse(user1);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'SUPPORT')")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> showUsers() {
        return userService.showUsers();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping({"/user/{username}"})
    public DeleteUserResponse deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @DeleteMapping({"/user/"})
    public DeleteUserResponse deleteUser() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/role")
    public UserResponse changeRole(@RequestBody RoleRequest request) {
        return userService.changeRole(request);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/access")
    public AccessResponse changeAccess(@RequestBody AccessRequest request) {
        return userService.changeAccess(request);
    }
}
