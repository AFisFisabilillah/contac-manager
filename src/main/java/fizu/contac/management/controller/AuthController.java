package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.LoginRequest;
import fizu.contac.management.model.TokenResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginRequest request){
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).message("Login Succes").build();
    }

    @DeleteMapping(path = "/api/user/logout",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder().message("berhail logout").data("OK").build();
    }

}
