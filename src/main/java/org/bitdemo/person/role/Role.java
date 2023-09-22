package org.bitdemo.person.role;

import jakarta.persistence.*;
import org.bitdemo.person.Person;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(mappedBy = "roles")
    private Set<Person> people;


    @Column(unique=true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        people = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getUsers() {
        return people;
    }

    public void setUsers(Set<Person> people) {
        this.people = people;
    }
}
