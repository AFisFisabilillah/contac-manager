package fizu.contac.management.repository;

import fizu.contac.management.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddresRepository extends JpaRepository<Address, String> {

}
