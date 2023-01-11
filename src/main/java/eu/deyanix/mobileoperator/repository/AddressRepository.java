package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
