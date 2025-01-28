package fizu.contac.management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.AddresResponse;
import fizu.contac.management.model.CreateAddresRequest;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.repository.AddresRepository;
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
                post("/api/contac/test/address/create")
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
                post("/api/contac/adajd9132013/address/create")
                        .header("X-API-TOKEN", user.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isNotFound()
        );

    }

}