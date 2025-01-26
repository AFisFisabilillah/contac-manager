package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.UpdateContacRequest;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.ContacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(
            path = "/api/contac/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContacResponse> getContac(User user, @PathVariable(name = "id") String id){
        return WebResponse.<ContacResponse>builder()
                .data(contacService.getContac(user, id))
                .message("berhasil menemukan contac dengan id "+id).build();
    }

    @PatchMapping(
            path = "/api/contac/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContacResponse> updateContac(User user,
                                                    @RequestBody UpdateContacRequest request,
                                                    @PathVariable(name = "id") String id){
        request.setId(id);
        return WebResponse.<ContacResponse>builder()
                .data(contacService.updateContac(user,request))
                .message("contac dengan id "+id+"berhasil di update").build();
    }
}
