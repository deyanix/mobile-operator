package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
