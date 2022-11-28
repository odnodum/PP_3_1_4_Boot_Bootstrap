package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Integer id);

    Set<Role> findRolesByName(String roleName);
}
