package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.LoginRequest;
import fizu.contac.management.model.TokenResponse;

public interface AuthService {
    public TokenResponse login(LoginRequest request);
}
