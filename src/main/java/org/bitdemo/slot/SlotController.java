package org.bitdemo.slot;

import org.bitdemo.person.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SlotController {

    @Autowired
    private SlotService slotService;

    @GetMapping(path = "/get-slots", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Slot> getSlots(){
        return slotService.getSlots();
    }

    /**
    * Insert a Slot. The String format is "2011-12-03T10:15:30+01:00". Remember to
    * encode the plus sign, '+', as %2b.
    * @param start the Slot start, a String in the format "2011-12-03T10:15:30+01:00"
    * @param finish the Slot finish, a String in the format "2011-12-03T10:15:30+01:00"
    */
    @PostMapping(path = "/insert-slot", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slot insertSlot(@Param("start") String start, @Param("finish") String finish){
        Slot slot = new Slot(start, finish);
        return slotService.insertSlot(slot);
    }



}
