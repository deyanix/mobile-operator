package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
