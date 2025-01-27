package fizu.contac.management.service;

import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.SearchContacRequest;
import fizu.contac.management.model.UpdateContacRequest;
import fizu.contac.management.repository.ContacRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteContac(User user, String id){
        long data = contacRepository.deleteByUserAndId(user, id);
        if( data == 0 ) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DATA TIDAK DI TEMUKAN");
    }

    @Transactional(readOnly = true)
    public Page<ContacResponse> search(User user, SearchContacRequest request){
        validation.validation(request);
        Specification<Contac> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));

            if(request.getName() != null){
                predicates.add(builder.or(
                        builder.like(root.get("firstName"), "%"+request.getName()+"%"),
                        builder.like(root.get("lastName"), "%"+request.getName()+"%")
                ));
            }

            if(request.getEmail() != null){
                predicates.add(builder.like(root.get("email"), "%"+request.getEmail()+"%"));
            }

            if(request.getPhone() != null){
                predicates.add(builder.like(root.get("phone"), "%"+request.getPhone()+"%"));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contac> allContac = contacRepository.findAll(specification, pageable);
        List<ContacResponse> contacResponses = allContac.stream().map(contac ->toContacResponse(contac)).toList();
        return new PageImpl<>(contacResponses,pageable,  allContac.getTotalElements());
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
