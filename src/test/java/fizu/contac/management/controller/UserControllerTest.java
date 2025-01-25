package fizu.contac.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fizu.contac.management.model.RegisterRequest;
import fizu.contac.management.model.WebResponse;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterSucces() throws Exception {
        RegisterRequest request = RegisterRequest.builder().username("rudeus").password("rahasia").name("afis Fisabilillah").build();
        String s = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> webResponse = objectMapper.readValue(result.getResponse().getContentAsString(), WebResponse.class);
            assertEquals("Login Berhasil", webResponse.getMessage());
        });

    }

    @Test
    void testRegisterBadRequest() throws Exception {

        RegisterRequest request = RegisterRequest.builder().username("").password("").name("").build();
        String s = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            System.out.println(result.getResponse().getContentAsString());
        });

    }

    @Test
    void testRegisterBadDuplikat() throws Exception {

        RegisterRequest request = RegisterRequest.builder().username("afisssssuuu").password("3213213").name("adadsads").build();
        String s = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andExpectAll(
                status().isBadRequest()
        );
    }
}