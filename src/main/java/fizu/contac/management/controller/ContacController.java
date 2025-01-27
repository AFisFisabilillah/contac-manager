package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.*;
import fizu.contac.management.service.ContacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping(
            path = "/api/contac/{id}/delete",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String>  deleteContac(User user, @PathVariable(name = "id") String id){
        contacService.deleteContac(user, id);
        return WebResponse.<String>builder().message("contac dengan id "+id+" berhasil di hapus").build();
    }

    @GetMapping(path = "/api/contacs", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ContacResponse>> search(User user,
                                                    @RequestParam(name = "name", required = false) String name,
                                                    @RequestParam(name = "email", required = false) String email,
                                                    @RequestParam(name = "phone", required = false) String phone,
                                                    @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                    @RequestParam(name = "page", required = false, defaultValue = "0") Integer page){
        SearchContacRequest searchContacRequest = new SearchContacRequest(name, phone, email, page, size);
        Page<ContacResponse> contacs = contacService.search(user, searchContacRequest);
        return WebResponse.<List<ContacResponse>>builder()
                .data(contacs.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contacs.getNumber())
                        .totalPage(contacs.getTotalPages())
                        .size(contacs.getSize()).build()).build();

    }
}
