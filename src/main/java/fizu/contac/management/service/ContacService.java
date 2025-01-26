package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.UpdateContacRequest;
import org.springframework.stereotype.Service;


public interface ContacService {
    public ContacResponse create(User user, ContacRequest request);
    public ContacResponse getContac(User user, String id);
    public ContacResponse updateContac(User user, UpdateContacRequest request);
    public void deleteContac(User user, String id);
}
