package org.bitdemo.slot;

import org.bitdemo.scheduler.timeSlot.TimeSlot;
import org.bitdemo.scheduler.timeSlot.TimeSlotValidator;

/**
 * Offers methods to translate a Slot to a TimeSlot and vice versa.
 */
public class SlotToTimeSlotAdapter {

    public static TimeSlot translate(Slot slot, TimeSlotValidator validator){
        return new TimeSlot(slot.getStart(), slot.getFinish(), validator);
    }

    public static Slot translate(TimeSlot timeSlot){
        return new Slot(timeSlot.getStart(), timeSlot.getEnd());
    }

}
