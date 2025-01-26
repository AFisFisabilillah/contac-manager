package fizu.contac.management.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContacRequest {
    @NotBlank
    private String firstname;

    @Size(max = 255)
    private String lastname;

    @Size(max = 255)
    @Email
    private String email;

    @Size(max = 255)
    @Pattern(
            regexp = "^(\\+62|62)?[-\\s]?0?8[1-9]\\d{1}[-\\s]?\\d{4}[-\\s]?\\d{2,5}$",
            message = "Nomor telepon tidak valid!"
    )    private String phone;
}
