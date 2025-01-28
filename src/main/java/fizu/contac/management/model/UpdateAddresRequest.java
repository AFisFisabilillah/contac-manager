package fizu.contac.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAddresRequest {
    @NotBlank
    @JsonIgnore
    private String id_contac;

    @NotBlank
    private String idAddres;

    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String province;

    @Size(max = 100)
    private String street;
}
