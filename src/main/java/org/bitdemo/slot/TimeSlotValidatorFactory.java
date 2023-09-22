package org.bitdemo.slot;

import org.bitdemo.scheduler.timeSlot.TimeSlotValidator;
import org.bitdemo.scheduler.timeSlot.TimeSlotValidator1Hour;

public class TimeSlotValidatorFactory {

    private static TimeSlotValidator timeSlotValidator;

    public static TimeSlotValidator getInstance(){
        if(timeSlotValidator == null)
            timeSlotValidator = new TimeSlotValidator1Hour();
        return timeSlotValidator;
    }
}
