package fizu.contac.management.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchContacRequest {
    private String name ;
    private String phone ;
    private String email ;

    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
