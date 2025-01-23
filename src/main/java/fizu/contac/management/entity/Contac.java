package fizu.contac.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="contacs")
public class Contac {
    @Id
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @Column(name = "phone", nullable = true, unique = true )
    private String phone;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private User user;

    @OneToMany(mappedBy = "contac")
    private List<Address> addresses;

}
