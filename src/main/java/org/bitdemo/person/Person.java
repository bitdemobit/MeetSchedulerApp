package org.bitdemo.person;

import jakarta.persistence.*;
import org.bitdemo.person.role.Role;
import org.bitdemo.slot.Slot;


import java.util.HashSet;
import java.util.Set;


/**
 * A Person which may have roles and availability for interviews.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true) // UNIQUE constraint should be applied to a field "email". Here for simplicity
    private String name;


    @ManyToMany
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    @ManyToMany//(cascade=CascadeType.ALL)(fetch = FetchType.EAGER )
    @JoinTable(
            name = "person_slot",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id")
    )
    private Set<Slot> slots;



    public Person(){};

    public Person(String name) {
        this.name = name;
        this.roles = new HashSet<>();
        this.slots = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role){
        return roles.remove(role);
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public boolean addSlot(Slot slot) {
        return slots.add(slot);
    }

    public boolean removeSlot(Slot slot){
        return slots.remove(slot);
    }


    public boolean hasRole(String role) {
        return roles.stream().filter( r -> r.getName().equals(role)).toList().isEmpty();
    }

    public boolean hasSlot(String start, String finish) {
        Slot slot = new Slot(start, finish);
        return !slots.stream()
                .filter( sl -> sl.getStart().equals(slot.getStart()) && sl.getFinish().equals(slot.getFinish()) )
                .toList()
                .isEmpty();
    }
}
