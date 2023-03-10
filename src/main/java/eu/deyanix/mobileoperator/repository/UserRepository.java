package eu.deyanix.mobileoperator.repository;

import eu.deyanix.mobileoperator.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
