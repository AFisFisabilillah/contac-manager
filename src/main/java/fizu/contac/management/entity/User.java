package fizu.contac.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "token_unique", columnNames = "token")
})
public class User {
    @Id
    @Column(nullable = false)
    private String username;

    private String password;

    private String name;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<Contac> contac;

}
