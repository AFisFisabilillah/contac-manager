package fizu.contac.management.controller;

import fizu.contac.management.entity.User;
import fizu.contac.management.model.*;
import fizu.contac.management.service.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddresService addresService;

    @PostMapping(
            path = "/api/contac/{id}/address",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddresResponse> createAddres(User user, @PathVariable(name = "id") String id, @RequestBody CreateAddresRequest request){
        request.setId_contac(id);
        AddresResponse addres = addresService.createAddres(user,request );

        return WebResponse.<AddresResponse>builder().message("berhasil menambah address").data(addres).build();
    }

    @PatchMapping(
            path = "/api/contac/{id}/address/{idAddres}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddresResponse> updateAddres(User user,
                                                    @PathVariable(name = "id") String id,
                                                    @PathVariable(name = "idAddres") String idAddres,
                                                    @RequestBody UpdateAddresRequest request
    ){
        request.setId_contac(id);
        request.setIdAddres(idAddres);
        AddresResponse addres = addresService.updateAddres(user,request);

        return WebResponse.<AddresResponse>builder().message("berhasil update address dengan id "+addres.getId()).data(addres).build();
    }
    @GetMapping(
            path = "/api/contac/{idContac}/address/{idAddres}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddresResponse> getAddres(User user , @PathVariable(name="idContac") String idContac, @PathVariable(name = "idAddres") String idAddres){
        return WebResponse.<AddresResponse>builder().data(addresService.getAddres(user,idContac, idAddres)).message("berhasil medapatkan addres dengann id "+idAddres).build();
    }

    @GetMapping(
            path = "/api/contac/{idContac}/address",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AddresResponse>> listAddres(User user , @PathVariable(name="idContac") String idContac){
        return WebResponse.<List<AddresResponse>>builder()
                .data(addresService.listAddres(user,idContac))
                .message("berhasil medapatkan addres dengann dari contac dengan  id "+idContac)
                .build();
    }

    @DeleteMapping(
            path = "/api/contac/{idContac}/address/{idAddres}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public  WebResponse<String> deleteAddres(User user,
                                             @PathVariable(name = "idContac") String idContac,
                                             @PathVariable(name = "idAddres") String idAddres
                                             )
    {
        addresService.deleteAddres(user, idAddres, idContac);
        return WebResponse.<String>builder()
                .message("Berhasi menghapus addres dengan id "+idAddres)
                .build();
    }


}
