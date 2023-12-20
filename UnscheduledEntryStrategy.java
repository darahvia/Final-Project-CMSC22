import java.time.LocalTime;
import java.util.List;
import java.util.Queue;

public class UnscheduledEntryStrategy {
/**
 * schedules unscheduled entries based on the available time slots and units per timeslot
 * @param unscheduledEntriesQueue   queue of unscheduled entries to be scheduled
 * @param entryManager              entry manager containing scheduled and unscheduled entries
 * @param timeblockManager          time block manager for managing the available time slots
 */

    // round robin implementation of alternating tasks based on units per time slot and total number of units
    public void scheduleUnscheduledEntries(Queue<UnscheduledEntry> unscheduledEntriesQueue, EntryManager entryManager, TimeblockManager timeblockManager) {
        while (!unscheduledEntriesQueue.isEmpty()){
            List<Integer> availableSlots = timeblockManager.getAvailableSlots();
            UnscheduledEntry unscheduledEntry = unscheduledEntriesQueue.peek();

            int timeslot = availableSlots.get(0);
            int unitsPerTimeslot = unscheduledEntry.getUnitsPerTimeslot();
            int unitsRemaining = unscheduledEntry.getUnitsRemaining();

            // check if its the last unitsPerTimeSlot based on the remaining units
            boolean lastIteration = (unitsPerTimeslot >= unitsRemaining);

            // calculate start time and end time based on the timeslot and units
            LocalTime startTime = calculateLocalTimeFromTimeslot(timeslot);
            LocalTime endTime = lastIteration ? calculateLocalTimeFromTimeslot(timeslot + unitsRemaining * 4) : calculateLocalTimeFromTimeslot(timeslot + unitsPerTimeslot * 4);

            int endSlot = entryManager.calculateSlot(endTime) - 1;              // adjustment for occupied slots
            int endDueSlot = entryManager.calculateSlot(unscheduledEntry.getDueTime());
            LocalTime endDueTime = unscheduledEntry.getDueTime();
            
            // adjust endSlot and endTime based on dueTime
            if (endDueSlot <= endSlot){ 
                endSlot = endDueSlot; 
                endTime = endDueTime;
            }  
            
            entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());             // add to the allEntries queue

            if (lastIteration){          // last iteration
                unscheduledEntriesQueue.poll().getName();               // no units remaining, time to dequeue
                continue;
            } else {;
                unscheduledEntry.decreaseUnits(unitsPerTimeslot);
                if (unitsRemaining > 0) {                               // requeue
                    unscheduledEntriesQueue.poll();
                    entryManager.getUnscheduledEntriesQueue().add(unscheduledEntry);
                }
            }
        }
    }

/**
 * calculate LocalTime based on the given time slot
 * @param timeslot  returns numeric value of the time slot from 0 to 95
 * @return          the calaculate LocalTime
 */
    private LocalTime calculateLocalTimeFromTimeslot(int timeslot) {
        int hour = timeslot / 4;
        int minute = (timeslot % 4) * 15;
        return LocalTime.of(hour, minute);
    }

/**
 * calculate numeric value of the timeslot within 0 to 95
 * @param time  LocalTime to be calculated 
 * @return      numeric value of the timeslot
 */
}
