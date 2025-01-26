package fizu.contac.management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.TypeRef;
import fizu.contac.management.entity.User;
import fizu.contac.management.model.ErrorResponse;
import fizu.contac.management.model.LoginRequest;
import fizu.contac.management.model.TokenResponse;
import fizu.contac.management.model.WebResponse;
import fizu.contac.management.repository.UserRepository;
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
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testLoginSucces()throws Exception{
        LoginRequest loginRequest = LoginRequest.builder().username("rudeus").password("rahasia").build();
        String request = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TokenResponse> webResponse = objectMapper.readValue(result.getResponse().getContentAsString(), WebResponse.class);
            assertEquals("Login Succes", webResponse.getMessage());
        });
    }

    @Test
    public void testLoginFailed()throws  Exception{
        LoginRequest loginRequest = LoginRequest.builder().username("posads").password("dsaad").build();
        String request = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    public void testLoginPasswordWrong()throws  Exception{
        LoginRequest loginRequest = LoginRequest.builder().username("rudeus").password("12adsadsa").build();
        String request = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(
                post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    void testLogoutSucces() throws Exception {
        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000000L);
        userRepository.save(user);

        mockMvc.perform(
                delete("/api/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")

        ).andExpect(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> stringWebResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertEquals("OK", stringWebResponse.getData());
        });
    }

    @Test
    void testLogoutFailed() throws Exception {


        mockMvc.perform(
                delete("/api/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                status().isUnauthorized()
        );
    }


}