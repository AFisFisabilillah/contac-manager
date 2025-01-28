package fizu.contac.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addreses")
public class Address {
    @Id
    private String id;

    private String street;

    private String city;

    private String province;

    private String country;

    @ManyToOne
    @JoinColumn(name = "contac_id", referencedColumnName = "id")
    private Contac contac;
}
