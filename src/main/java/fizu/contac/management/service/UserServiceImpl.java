package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.RegisterRequest;
import fizu.contac.management.model.UpdateUserRequest;
import fizu.contac.management.model.UserResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.repository.UserRepository;
import fizu.contac.management.security.BCrypt;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidateService validation;

    @Transactional
    public void register(RegisterRequest request) {

        validation.validation(request);

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    public UserResponse getUser(User user){
        return UserResponse.builder().name(user.getName()).username(user.getUsername()).build();
    }



    @Transactional
    public UserResponse updateUser(User user, UpdateUserRequest request){
        validation.validation(request);


        if(request.getName() != null){
            user.setName(request.getName());
        }

        userRepository.save(user);

       return new UserResponse(user.getUsername(), user.getName());
    }


}
