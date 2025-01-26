package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.ContacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContacController {

    @Autowired
    private ContacService contacService;

    @PostMapping(
            path = "/api/contac/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContacResponse> createContac(User user, @RequestBody ContacRequest request){
        return WebResponse.<ContacResponse>builder().data(contacService.create(user, request)).message("berhasil menambahkan Contac").build();
    }
}
