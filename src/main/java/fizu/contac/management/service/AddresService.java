package fizu.contac.management.service;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.UpdateAddresRequest;

public interface AddresService {
    public AddresResponse createAddres(User user, CreateAddresRequest request);
    public AddresResponse updateAddres(User user, UpdateAddresRequest request);
}
