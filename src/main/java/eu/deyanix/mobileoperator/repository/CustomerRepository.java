package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
//	@Modifying
//	@Query("DELETE FROM Customer c where c = ?1")
//	void delete(Customer customer);
}
