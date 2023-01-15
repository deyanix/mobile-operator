package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long>, PagingAndSortingRepository<Offer, Long> {
	@Query("SELECT o FROM Offer o LEFT JOIN o.mobileOffer mo WHERE o.mobileOffer IS NULL")
	Iterable<Offer> findAllInternet();

	@Query("SELECT o FROM Offer o INNER JOIN o.mobileOffer mo WHERE o.mobileOffer IS NOT NULL")
	Iterable<Offer> findAllMobile();
}

