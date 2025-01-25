package fizu.contac.management.model;

import fizu.contac.management.repository.UserRepository;
import fizu.contac.management.validation.annotation.Unique;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    @Size(max = 100)
    private String name;
}
