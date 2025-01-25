package fizu.contac.management.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidateService {
    @Autowired
    private Validator validator;

    public void validation(Object request){
        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if(violations.size() > 0){
            throw new ConstraintViolationException(violations);
        }
    }
}
