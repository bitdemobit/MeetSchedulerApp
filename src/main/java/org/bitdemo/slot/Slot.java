package org.bitdemo.slot;

import jakarta.persistence.*;
import org.bitdemo.person.Person;
import org.hibernate.internal.util.ZonedDateTimeComparator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint( name = "UniqueStartAndFinish",
                                                columnNames = { "start", "finish" }) })
public class Slot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private ZonedDateTime start;
    private ZonedDateTime finish;


    @ManyToMany(mappedBy = "slots")
    private Set<Person> people;

    public Slot() {
    }

    public Slot(ZonedDateTime start, ZonedDateTime finish) {
        this.start = start;
        this.finish = finish;
        people = new HashSet<>();
    }

    /**
     * Constructs a Slot from strings
     * @param start the Slot start, a String in the format "2011-12-03T10:15:30+01:00"
     * @param end the Slot end, a String in the format "2011-12-03T10:15:30+01:00"
     */
    public Slot(String start, String end){
        this.start = ZonedDateTime.parse(start);
        this.finish = ZonedDateTime.parse(end);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getFinish() {
        return finish;
    }

    public void setFinish(ZonedDateTime finish) {
        this.finish = finish;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public boolean addPerson(Person person) {
        return people.add(person);
    }

    public boolean removePerson(Person person){
        return people.remove(person);
    }


}
