package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
