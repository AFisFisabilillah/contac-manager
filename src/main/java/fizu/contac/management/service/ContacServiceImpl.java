package fizu.contac.management.service;

import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.UpdateContacRequest;
import fizu.contac.management.repository.ContacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ContacServiceImpl implements ContacService{

    @Autowired
    private ValidateService validation;

    @Autowired
    private ContacRepository contacRepository;

    @Override
    @Transactional
    public ContacResponse create(User user, ContacRequest request) {
        validation.validation(request);

        Contac contac = new Contac();
        contac.setId(UUID.randomUUID().toString());
        contac.setFirstName(request.getFirstname());
        contac.setLastName(request.getLastname());
        contac.setEmail(request.getEmail());
        contac.setPhone(request.getPhone());
        contac.setUser(user);

        contacRepository.save(contac);
        return toContacResponse(contac);
    }

    @Transactional(readOnly = true)
    public ContacResponse getContac(User user, String id){
        Contac contac = contacRepository.findByUserAndId(user, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cotac dengan id " + id + " not found"));
        return toContacResponse(contac);
    }

    @Transactional
    public ContacResponse updateContac(User user, UpdateContacRequest request){
        validation.validation(request);
        Contac contac = contacRepository.findByUserAndId(user, request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "contac tidak di temukan"));
         if(request.getFirstname() != null) contac.setFirstName(request.getFirstname());
         if(request.getLastname() != null) contac.setLastName(request.getLastname());
         if(request.getEmail() != null) contac.setEmail(request.getEmail());
         if(request.getPhone() != null) contac.setPhone(request.getPhone());

         contacRepository.save(contac);
         return toContacResponse(contac);
    }

    private ContacResponse toContacResponse(Contac contac){
       return ContacResponse.builder()
                .email(contac.getEmail())
                .id(contac.getId())
                .firstname(contac.getFirstName())
                .lastname(contac.getLastName())
                .phone(contac.getPhone()).build();
    }
}
