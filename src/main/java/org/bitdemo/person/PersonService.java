package org.bitdemo.person;

import jakarta.transaction.Transactional;
import org.bitdemo.person.role.Role;
import org.bitdemo.person.role.RoleService;
import org.bitdemo.scheduler.Scheduler;
import org.bitdemo.scheduler.timeSlot.TimeSlot;
import org.bitdemo.scheduler.timeSlot.TimeSlotValidator;
import org.bitdemo.slot.Slot;
import org.bitdemo.slot.SlotService;
import org.bitdemo.slot.SlotToTimeSlotAdapter;
import org.bitdemo.slot.TimeSlotValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SlotService slotService;


    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person insertPerson(String name){
        return personRepository.findByName(name).orElseGet( () -> personRepository.save(new Person(name)) );
    }

    public Optional<Person> getPerson(String name){
        return personRepository.findByName(name);
    }

    /**
     * Gets the overlapping slots between a set of persons.
     * @param names the persons names
     * @return a List of Slot objects
     */
    public List<Slot> getPersonsOverlaps(List<String> names) {
        LinkedList<List<TimeSlot>> slots    = new LinkedList<>();
        LinkedList<TimeSlot> personalSlots  = new LinkedList<>();
        TimeSlotValidator validator = TimeSlotValidatorFactory.getInstance();

        Optional<Person> p;
        for(String name: names){
            p = personRepository.findByNameIncludeSlots(name);
            //for(Slot slot: p.get().getSlots()){
            //    personalSlots.add( SlotToTimeSlotAdapter.translate(slot, validator) );
            //}
            p.ifPresent(person -> slots.add(person.getSlots().stream()
                    .map(slot -> SlotToTimeSlotAdapter.translate(slot, validator)).toList()));
            //slots.add(personalSlots);
            //personalSlots = new LinkedList<>();
        }

        return Scheduler.findOverlaps(slots)                        // returns TimeSlot collection
                .stream().map(SlotToTimeSlotAdapter::translate)     // convert to Slot collection
                .toList();                                          // and return a list
    }


    /**
     * Associate a Person with a given Role. If either Person or Role
     * do not exist, they are created.
     * @param name the Person name
     * @param role the Role name
     * @return the Person
     */
    @Transactional
    public Person insertPersonRole(String name, String role) {
        Optional<Person> p = personRepository.findByNameIncludeRoles(name);
        Person person;
        Role newRole;
        if(p.isPresent()){                          // person exists in the database
            person = p.get();
            if ( !person.hasRole(role) ){           // person does not have this role
                newRole = roleService.findByName(role).orElseGet(() -> roleService.insertRole(role));
                person.addRole(newRole);
                personRepository.save(person);
            }
        }
        else{                                           // person does NOT exist in the database
            person = new Person(name);
            newRole = roleService.findByName(role).orElseGet(() -> roleService.insertRole(role));
            person.addRole(newRole);
            personRepository.save(person);
        }

        return person;
    }


    /**
     * Associate a Person with a given Slot. If either Person or Role
     * do not exist, they are created. Strings that represent slots
     * must be in the format "2011-12-03T10:15:30+01:00". Remember to
     * encode the plus sign, '+', as %2b in the http request.
     * @param name the Person name
     * @param start the start of the slot
     * @param finish the end of the slot
     * @return the Person
     */
    public Person insertPersonSlot(String name, String start, String finish) {
        Optional<Person> p = personRepository.findByNameIncludeSlots(name);
        Person person;
        Slot newSlot;
        if(p.isPresent()){                          // person exists in the database
            person = p.get();
            if ( !person.hasSlot(start, finish) ){  // person does not have this slot
                newSlot = slotService.findByStartAndFinish(start, finish).orElseGet(() -> slotService.insertSlot(start, finish));
                person.addSlot(newSlot);
                personRepository.save(person);
            }
        }
        else{                                       // person does NOT exist in the database
            person = new Person(name);
            newSlot = slotService.findByStartAndFinish(start, finish).orElseGet(() -> slotService.insertSlot(start, finish));
            person.addSlot(newSlot);
            personRepository.save(person);
        }
        return person;
    }
}
