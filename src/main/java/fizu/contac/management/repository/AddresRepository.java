package fizu.contac.management.repository;

import fizu.contac.management.entity.Address;
import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddresRepository extends JpaRepository<Address, String> {
    Optional<Address> findByContac_IdAndIdAndContac_User(String idContac, String id, User user);

}
