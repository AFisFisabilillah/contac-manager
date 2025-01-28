package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;

public interface AddresService {
    public AddresResponse createAddres(User user, CreateAddresRequest request);
}
