package fizu.contac.management.controller;

import fizu.contac.management.model.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ErrorControllerUnitTest {

    private final ErrorController errorController = new ErrorController();

    @Test
    void constraintViolationException_shouldReturnBadRequestWithErrorsMap() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        PathStub propertyPath = new PathStub("username");

        when(violation.getPropertyPath()).thenReturn(propertyPath);
        when(violation.getMessage()).thenReturn("must not be blank");

        Set<ConstraintViolation<?>> violations = new LinkedHashSet<>();
        violations.add(violation);

        ConstraintViolationException exception = new ConstraintViolationException(violations);

        var response = errorController.constraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Maaf ada error", response.getBody().getMessage());
        assertEquals("must not be blank", response.getBody().getErrors().get("username"));
    }

    @Test
    void responseStatusException_shouldRespectStatusAndMessage() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");

        var response = errorController.responseStatusException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Data not found", response.getBody().getMessage());
        assertNull(response.getBody().getErrors());
    }

    @Test
    void messageNotReadableException_shouldReturnBadRequest() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("invalid payload");

        var response = errorController.messageNotReadableException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Malformed JSON request", response.getBody().getMessage());
    }

    @Test
    void methodArgumentNotValidException_shouldReturnFieldErrors() throws Exception {
        TestPayload payload = new TestPayload();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(payload, "payload");
        bindingResult.addError(new FieldError("payload", "email", "must be a well-formed email address"));

        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        var response = errorController.methodArgumentNotValidException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse<Map<String, String>> body = response.getBody();
        assertEquals("Validation failed", body.getMessage());
        assertEquals("must be a well-formed email address", body.getErrors().get("email"));
    }

    @Test
    void uncaughtException_shouldReturnInternalServerError() {
        var response = errorController.uncaughtException(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
    }

    private static class TestPayload {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private record PathStub(String value) implements jakarta.validation.Path {
        @Override
        public String toString() {
            return value;
        }

        @Override
        public java.util.Iterator<Node> iterator() {
            return java.util.Collections.emptyIterator();
        }
    }
}
