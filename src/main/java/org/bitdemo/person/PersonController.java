package org.bitdemo.person;


import org.bitdemo.slot.Slot;
import org.bitdemo.slot.SlotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(path = "/get-persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPersons(){
        return personService.getPersons();
    }


    @PostMapping(path = "/insert-person", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person insertPerson(@Param("name") String name){
        return personService.insertPerson(name);
    }


    @GetMapping(path = "/get-persons-overlaps", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Slot> getPersonsOverlaps(@RequestParam List<String> names){
        return personService.getPersonsOverlaps(names);
    }


    @PostMapping(path = "/insert-person-availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertPersonAvailability(@Param("name") String name,
                                           @Param("start") String start,
                                           @Param("finish") String finish){
        Person p = personService.insertPersonSlot(name, start, finish);
        return "OK"; // TODO decide what to return
    }


    @PostMapping(path = "/insert-person-role", produces = MediaType.APPLICATION_JSON_VALUE)
    public String insertPersonRole(@Param("name") String name, @Param("role") String role){
        Person p = personService.insertPersonRole(name, role);
        return "OK"; // TODO decide what to return
    }


}
