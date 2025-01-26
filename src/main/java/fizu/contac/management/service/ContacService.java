package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import org.springframework.stereotype.Service;


public interface ContacService {
    public ContacResponse create(User user, ContacRequest request);
}
