package fizu.contac.management.controller;

import fizu.contac.management.model.RegisterRequest;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
