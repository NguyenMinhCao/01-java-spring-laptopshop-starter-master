package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    List<User> findAll();

    void deleteById(Long id);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
