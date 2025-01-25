package fizu.contac.management.service;


import fizu.contac.management.entity.User;
import fizu.contac.management.model.LoginRequest;
import fizu.contac.management.model.TokenResponse;
import fizu.contac.management.repository.UserRepository;
import fizu.contac.management.security.BCrypt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidateService validateService;

    @Transactional
    public TokenResponse login(LoginRequest request){
        validateService.validation(request);

        User user = userRepository.findById(request.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,  "Useraname or Password wrong"));
        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(this.next30Days());
            userRepository.save(user);
            return new TokenResponse(user.getToken(), user.getTokenExpiredAt());
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Useraname or Password wrong");
        }
    }

    private Long next30Days(){
        return System.currentTimeMillis()+ 1000*60*24*30;
    }
}
