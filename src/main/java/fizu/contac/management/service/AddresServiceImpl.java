package fizu.contac.management.service;

import fizu.contac.management.entity.Address;
import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.UpdateAddresRequest;
import fizu.contac.management.repository.AddresRepository;
import fizu.contac.management.repository.ContacRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AddresServiceImpl implements AddresService {
    private final ValidateService validation;
    private final AddresRepository addresRepository;
    private final ContacRepository contacRepository;

    public AddresServiceImpl(ValidateService validation, AddresRepository addresRepository, ContacRepository contacRepository) {
        this.validation = validation;
        this.addresRepository = addresRepository;
        this.contacRepository = contacRepository;
    }

    @Transactional
    public AddresResponse createAddres(User user, CreateAddresRequest request) {
        validation.validation(request);

        Contac contac = contacRepository.findByUserAndId(user, request.getId_contac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contac tidak di temukan"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContac(contac);
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setStreet(request.getStreet());

        addresRepository.save(address);

        return toAddresResponse(address);
    }

    @Transactional
    public AddresResponse updateAddres(User user, UpdateAddresRequest request) {
        validation.validation(request);
        Address address = addresRepository
                .findByContac_IdAndIdAndContac_User(request.getId_contac(), request.getIdAddres(), user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maaf id addres tidak ketemu"));

        if (request.getCountry() != null) address.setCountry(request.getCountry());
        if (request.getProvince() != null) address.setProvince(request.getProvince());
        if (request.getCity() != null) address.setCity(request.getCity());
        if (request.getStreet() != null) address.setStreet(request.getStreet());

        addresRepository.save(address);

        return toAddresResponse(address);
    }

    @Transactional(readOnly = true)
    public AddresResponse getAddres(User user, String idContac, String idAddres) {
        Address address = addresRepository
                .findByContac_IdAndIdAndContac_User(idContac, idAddres, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maaf id addres tidak ketemu"));
        return toAddresResponse(address);
    }

    @Transactional(readOnly = true)
    public List<AddresResponse> listAddres(User user, String idContac) {
        Contac contac = contacRepository.findByUserAndId(user, idContac)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maaf id contac  tidak ketemu"));
        List<Address> addresses = contac.getAddresses();
        return addresses.stream().map(this::toAddresResponse).toList();
    }

    @Transactional
    public void deleteAddres(User user, String idAddres, String idContac) {
        Contac contac = contacRepository.findByUserAndId(user, idContac)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maaf id contac  tidak ketemu"));

        Integer isSucces = addresRepository.deleteByContacAndId(contac, idAddres);
        if (isSucces == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "maaf idAddres tidak di temukan ");
        }
    }

    private AddresResponse toAddresResponse(Address address) {
        return AddresResponse.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .province(address.getProvince())
                .street(address.getStreet())
                .build();
    }
}
