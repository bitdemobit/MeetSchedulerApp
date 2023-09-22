package org.bitdemo.person;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByName(String name);

    /**
     * Retrieves a Person including their respective availability slots
     * @param id the Person id
     * @return an Optional with a Person or empty
     */
    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.slots s WHERE p.id = ?1")
    public Optional<Person> findByIdIncludeSlots(long id);

    /**
     * Retrieves a Person including their respective availability slots
     * @param name the Person name
     * @return an Optional with a Person or empty
     */
    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.slots s WHERE p.name = ?1")
    public Optional<Person> findByNameIncludeSlots(@Param("name") String name);

    /**
     * Retrieves a Person including their respective Roles
     * @param name the Person name
     * @return an Optional with Person or empty
     */
    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.roles r WHERE p.name = ?1")
    public Optional<Person> findByNameIncludeRoles(String name);
}