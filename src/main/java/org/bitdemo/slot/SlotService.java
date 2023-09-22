package org.bitdemo.slot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SlotService {
    @Autowired
    private SlotRepository slotRepository;


    public List<Slot> getSlots() {
        return slotRepository.findAll();
    }

    public Slot insertSlot(Slot slot){
        return slotRepository.save(slot);
    }

    public Slot insertSlot(String start, String finish) {return slotRepository.save(new Slot(start, finish));}

    public Optional<Slot> findByStartAndFinish(String start, String finish) {
        Slot slot = new Slot(start, finish);
        return slotRepository.findByStartAndFinish(slot.getStart(), slot.getFinish());
    }
}
