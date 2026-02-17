package fizu.contac.management.security.auth;

import fizu.contac.management.entity.User;
import fizu.contac.management.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenAuthenticationFilterTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSetAuthenticationForValidToken() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(userRepository);

        User user = new User();
        user.setUsername("test-user");
        user.setToken("valid-token");
        user.setTokenExpiredAt(System.currentTimeMillis() + 60_000);

        when(userRepository.findByToken("valid-token")).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/profile");
        request.addHeader("X-API-TOKEN", "valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void shouldNotSetAuthenticationForExpiredToken() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(userRepository);

        User user = new User();
        user.setUsername("test-user");
        user.setToken("expired-token");
        user.setTokenExpiredAt(System.currentTimeMillis() - 60_000);

        when(userRepository.findByToken("expired-token")).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/profile");
        request.addHeader("X-API-TOKEN", "expired-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldSkipPublicEndpoint() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        TokenAuthenticationFilter filter = new TokenAuthenticationFilter(userRepository);

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/auth/login");
        request.addHeader("X-API-TOKEN", "any-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        verify(userRepository, never()).findByToken(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
