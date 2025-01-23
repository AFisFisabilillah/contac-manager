package fizu.contac.management.service;

import fizu.contac.management.model.RegisterRequest;
import org.springframework.stereotype.Service;

public interface UserService {
    public void register(RegisterRequest request);
}
