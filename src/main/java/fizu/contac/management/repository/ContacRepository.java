package fizu.contac.management.repository;

import fizu.contac.management.entity.Contac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContacRepository extends JpaRepository<Contac, String> {

}
