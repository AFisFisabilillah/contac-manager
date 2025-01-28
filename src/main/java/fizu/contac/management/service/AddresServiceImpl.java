package fizu.contac.management.service;

import fizu.contac.management.entity.Address;
import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.repository.AddresRepository;
import fizu.contac.management.repository.ContacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AddresServiceImpl implements AddresService{
    @Autowired
    private ValidateService validation;
    @Autowired
    private AddresRepository addresRepository;
    @Autowired
    private ContacRepository contacRepository;

    public AddresResponse createAddres(User user, CreateAddresRequest request){
        validation.validation(request);

        Contac contac = contacRepository.findByUserAndId(user, request.getId_contac()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contac tidak di temukan"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContac(contac);
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setStreet(request.getStreet());

        addresRepository.save(address);

        return  toAddresResponse(address);
    }

    private AddresResponse toAddresResponse(Address address){
        return AddresResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .province(address.getProvince())
                .street(address.getStreet())
                .build();
    }
}
