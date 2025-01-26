package fizu.contac.management.repository;

import fizu.contac.management.entity.Contac;
import fizu.contac.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContacRepository extends JpaRepository<Contac, String> {
    Optional<Contac> findByUserAndId(User user, String id);

}
