package fizu.contac.management.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Username Harus di isi")
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 100, min = 6)
    private String password;

    @NotBlank
    private String name;

}
