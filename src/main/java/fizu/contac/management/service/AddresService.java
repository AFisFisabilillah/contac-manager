package fizu.contac.management.service;

import fizu.contac.management.entity.Address;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.UpdateAddresRequest;

import java.util.List;

public interface AddresService {
    public AddresResponse createAddres(User user, CreateAddresRequest request);
    public AddresResponse updateAddres(User user, UpdateAddresRequest request);
    public AddresResponse getAddres(User user, String idContac, String idAddres);
    public List<AddresResponse> listAddres(User user, String idContac);
}
