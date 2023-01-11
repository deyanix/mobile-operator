package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Agreement;
import eu.deyanix.mobileoperator.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends CrudRepository<Agreement, Long>, PagingAndSortingRepository<Agreement, Long> {
	Page<Agreement> findAllByCustomer(Customer customer, Pageable pageable);

	@Query("SELECT a FROM Agreement a LEFT JOIN a.offer.mobileOffer mo WHERE a.customer = ?1 AND mo IS NULL")
	Page<Agreement> findAllInternetByCustomer(Customer customer, Pageable pageable);

	@Query("SELECT a FROM Agreement a INNER JOIN a.offer.mobileOffer mo WHERE a.customer = ?1")
	Page<Agreement> findAllMobileByCustomer(Customer customer, Pageable pageable);
}
