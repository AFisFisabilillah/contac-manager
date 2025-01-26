package fizu.contac.management.service;

import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.repository.ContacRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ContacResponse.builder()
                .email(contac.getEmail())
                .id(contac.getId())
                .firstname(contac.getFirstName())
                .lastname(contac.getLastName())
                .phone(contac.getPhone()).build();
    }
}
