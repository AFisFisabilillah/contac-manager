package fizu.contac.management.model;

import fizu.contac.management.repository.UserRepository;
import fizu.contac.management.validation.annotation.Unique;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username Harus di isi")
    @Size(max = 100)
    @Unique(repository = UserRepository.class, fieldName = "username")
    private String username;

    @NotBlank
    @Size(max = 100, min = 6)
    private String password;

    @NotBlank
    private String name;

}
