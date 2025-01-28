package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.service.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;

@RestController
public class AddressController {

    @Autowired
    private AddresService addresService;

    @PostMapping(
            path = "/api/contac/{id}/address/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddresResponse> createAddres(User user, @PathVariable(name = "id") String id, @RequestBody CreateAddresRequest request){
        request.setId_contac(id);
        AddresResponse addres = addresService.createAddres(user,request );

        return WebResponse.<AddresResponse>builder().message("berhasil menambah address").data(addres).build();
    }
}
