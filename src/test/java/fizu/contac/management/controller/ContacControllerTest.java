package fizu.contac.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.ContacRequest;
import fizu.contac.management.model.ContacResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.repository.ContacRepository;
import fizu.contac.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContacControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContacRepository contacRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        contacRepository.deleteAll();
    }

    @Test
    public void testCreateContacSucces() throws Exception {
        ContacRequest contacRequest = new ContacRequest("afis", "fisabilillah", "afis@gmail.com", "+628123456789");
        String request = objectMapper.writeValueAsString(contacRequest);
        mockMvc.perform(
                post("/api/contac/create")
                        .header("X-API-TOKEN", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContacResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertEquals("berhasil menambahkan Contac", response.getMessage());

        });
    }

    @Test
    public void testCreateContacFailedValidation() throws Exception {
        ContacRequest contacRequest = new ContacRequest("afis", "", "afis@ahaha.asdas", "+628789");
        String request = objectMapper.writeValueAsString(contacRequest);
        mockMvc.perform(
                post("/api/contac/create")
                        .header("X-API-TOKEN", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isBadRequest()
        ).andDo(result -> {

            System.out.println(result.getResponse().getContentAsString());
        });
    }

    @Test
    public void testCreateContacFailedAuth() throws Exception {
        ContacRequest contacRequest = new ContacRequest("afis", "", "afis@ahaha.asdas", "+628789");
        String request = objectMapper.writeValueAsString(contacRequest);
        mockMvc.perform(
                post("/api/contac/create")
                        .header("X-API-TOKEN","dsa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isUnauthorized()
        ).andDo(result -> {

            System.out.println(result.getResponse().getContentAsString());
        });
    }

    @Test
    public  void testGetContacSucces()throws Exception{
        User user = userRepository.findById("test").orElseThrow();
        Contac contac = new Contac();
        contac.setId("2112");
        contac.setFirstName("afis");
        contac.setEmail("afis@gmail.com");
        contac.setPhone("121212121");
        contac.setUser(user);
        contacRepository.save(contac);

        mockMvc.perform(
                get("/api/contac/2112")
                        .header("X-API-TOKEN", user.getToken())
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ContacResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<ContacResponse>>() {
            });
            assertEquals("berhasil menemukan contac dengan id "+response.getData().getId(), response.getMessage());
        });

    }

    @Test
    public  void testGetContacFailedNotFound()throws Exception{
        User user = userRepository.findById("test").orElseThrow();

        mockMvc.perform(
                get("/api/contac/21wqewq12")
                        .header("X-API-TOKEN", user.getToken())
        ).andExpect(
                status().isNotFound()
        );

    }

}