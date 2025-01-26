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
public class ContacResponse {

    private String id;

    private String firstname;


    private String lastname;


    private String email;

    private String phone;
}
