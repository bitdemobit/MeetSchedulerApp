package org.bitdemo.person.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(String name);
}
