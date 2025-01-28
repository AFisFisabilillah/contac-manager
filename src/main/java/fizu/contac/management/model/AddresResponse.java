package fizu.contac.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddresResponse {
    private String id;
    private String country;
    private String province;
    private String city;
    private String street;
}
