package antifraud.service.UserService;

import antifraud.model.User.Operations;
import antifraud.model.User.Request.AccessRequest;
import antifraud.model.User.Request.RoleRequest;
import antifraud.model.User.Response.AccessResponse;
import antifraud.model.User.Response.DeleteUserResponse;
import antifraud.model.User.Response.UserResponse;
import antifraud.model.User.User;
import antifraud.model.User.UserRoles;
import antifraud.repository.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (userRepo.count() == 0) {
            user.setRole(UserRoles.ADMINISTRATOR);
            user.setLocked(false);
        } else {
            user.setRole(UserRoles.MERCHANT);
            user.setLocked(true);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);

    }

    @Override
    public List<UserResponse> showUsers() {
        List<UserResponse> allUsers = new ArrayList<>();
        userRepo.findAll().forEach(
                user -> allUsers.add(new UserResponse(user)));
        return allUsers;
    }

    @Transactional
    @Override
    public DeleteUserResponse deleteUser(String username) {
        if (userRepo.deleteByUsername(username) > 0) {
            return new DeleteUserResponse(username, "Deleted successfully!");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserResponse changeRole(RoleRequest request) {
        if (!userRepo.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (request.getRole() == UserRoles.ADMINISTRATOR || request.getRole() == UserRoles.ANONYMOUS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = userRepo.findByUsername(request.getUsername());
        if (request.getRole() == user.getRole()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.setRole(request.getRole());
        userRepo.save(user);
        return new UserResponse(user);
    }

    @Override
    public AccessResponse changeAccess(AccessRequest request) {
        if (!userRepo.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = userRepo.findByUsername(request.getUsername());
        String message;
        if (user.getRole() == UserRoles.ADMINISTRATOR){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(request.getOperation()== Operations.UNLOCK){
            user.setLocked(false);
            message = "User "+user.getUsername()+" unlocked!";
        }else {
            user.setLocked(true);
            message = "User "+user.getUsername()+" locked!";
        }
        userRepo.save(user);
            return new AccessResponse(message);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userToReturn = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().toString())
                .accountLocked(user.isLocked())
                .build();
        return userToReturn;
    }

}
