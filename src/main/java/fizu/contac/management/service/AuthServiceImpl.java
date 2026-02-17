package fizu.contac.management.service;


import fizu.contac.management.entity.User;
import fizu.contac.management.model.LoginRequest;
import fizu.contac.management.model.TokenResponse;
import fizu.contac.management.repository.UserRepository;
import fizu.contac.management.security.BCrypt;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final ValidateService validateService;

    public AuthServiceImpl(UserRepository userRepository, ValidateService validateService) {
        this.userRepository = userRepository;
        this.validateService = validateService;
    }

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

    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }


    private Long next30Days(){
        return System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30);
    }
}
