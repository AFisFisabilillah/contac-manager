package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.RegisterRequest;
import fizu.contac.management.model.UpdateUserRequest;
import fizu.contac.management.model.UserResponse;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

public interface UserService {
    public void register(RegisterRequest request);
    public UserResponse getUser(User user);
    public UserResponse updateUser(User user, UpdateUserRequest request);
}
