package vn.hoidanit.laptopshop.repository;

import org.springframework.data.repository.CrudRepository;

import vn.hoidanit.laptopshop.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
