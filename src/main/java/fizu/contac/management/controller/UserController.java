package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.RegisterRequest;
import fizu.contac.management.model.UpdateUserRequest;
import fizu.contac.management.model.UserResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("Ok").message("Login Berhasil").build();
    }

    @GetMapping(path = "/api/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(User user){
        return userService.getUser(user);
    }

    @PatchMapping(
            path = "/api/profile/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> updateProfile(User user, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.updateUser(user, request);
        return WebResponse.<UserResponse>builder()
                .message("Update Berasil")
                .data(userResponse).build();
    }
}
