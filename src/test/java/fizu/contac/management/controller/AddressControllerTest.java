package fizu.contac.management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fizu.contac.management.entity.Address;
import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.UpdateAddresRequest;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.repository.AddresRepository;
import fizu.contac.management.repository.ContacRepository;
import fizu.contac.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddresRepository addresRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContacRepository contacRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        addresRepository.deleteAll();
    }

    @Test
    void testCreateAddresSucces()throws IOException, Exception {
        User user = userRepository.findById("test").orElseThrow();

        CreateAddresRequest requestAddres = CreateAddresRequest.builder().id_contac("test").country("jepang").city("osaka").province("Okinawa").street("gg saon").build();
        String request = objectMapper.writeValueAsString(requestAddres);

        mockMvc.perform(
                post("/api/contac/test/address")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddresResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<AddresResponse>>() {
            });
            assertEquals(response.getData().getCity(), requestAddres.getCity());
            assertEquals(response.getData().getCountry(), requestAddres.getCountry());
            assertEquals(response.getData().getProvince(), requestAddres.getProvince());
        });
    }

    @Test
    void testCreateAddresFailedContacNotFound()throws IOException, Exception{
        User user = userRepository.findById("test").orElseThrow();

        CreateAddresRequest requestAddres = CreateAddresRequest.builder().id_contac("test").country("jepang").city("osaka").province("Okinawa").street("gg saon").build();
        String request = objectMapper.writeValueAsString(requestAddres);

        mockMvc.perform(
                post("/api/contac/adajd9132013/address")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void testUpdateAddresSucces()throws IOException, Exception{
        User user = userRepository.findById("test").orElseThrow();

        Contac contac = contacRepository.findByUserAndId(user, "test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setCountry("indonesia");
        address.setProvince("jwa barat");
        address.setCity("bogor");
        address.setStreet("jln bogor raya");
        address.setContac(contac);
        addresRepository.save(address);


        UpdateAddresRequest requestAddres = UpdateAddresRequest.builder().id_contac("test").country("malaysia").city("kauala lumpr").province("upin").street("jl durian runtuh").build();
        String request = objectMapper.writeValueAsString(requestAddres);

        mockMvc.perform(
                patch("/api/contac/test/address/test")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<AddresResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<AddresResponse>>() {
            });
            assertEquals(response.getData().getCity(), requestAddres.getCity());
            assertEquals(response.getData().getCountry(), requestAddres.getCountry());
            assertEquals(response.getData().getProvince(), requestAddres.getProvince());
            assertEquals(response.getData().getStreet(), requestAddres.getStreet());
        });;

    }

    @Test
    void testUpdateAddresFailedNotfound()throws IOException, Exception{
        User user = userRepository.findById("test").orElseThrow();

        Contac contac = contacRepository.findByUserAndId(user, "test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setCountry("indonesia");
        address.setProvince("jwa barat");
        address.setCity("bogor");
        address.setStreet("jln bogor raya");
        address.setContac(contac);
        addresRepository.save(address);


        UpdateAddresRequest requestAddres = UpdateAddresRequest.builder().id_contac("test").country("malaysia").city("kauala lumpr").province("upin").street("jl durian runtuh").build();
        String request = objectMapper.writeValueAsString(requestAddres);

        mockMvc.perform(
                patch("/api/contac/test/address/dsasads")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isNotFound()
        );

    }

    @Test
    void testUpdateAddresFailedNotfoundContac()throws IOException, Exception{
        User user = userRepository.findById("test").orElseThrow();

        Contac contac = contacRepository.findByUserAndId(user, "test").orElseThrow();

        Address address = new Address();
        address.setId("test");
        address.setCountry("indonesia");
        address.setProvince("jwa barat");
        address.setCity("bogor");
        address.setStreet("jln bogor raya");
        address.setContac(contac);
        addresRepository.save(address);


        UpdateAddresRequest requestAddres = UpdateAddresRequest.builder().id_contac("test").country("malaysia").city("kauala lumpr").province("upin").street("jl durian runtuh").build();
        String request = objectMapper.writeValueAsString(requestAddres);

        mockMvc.perform(
                patch("/api/contac/2jdsakdsa/address/test")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isNotFound()
        );

    }



}