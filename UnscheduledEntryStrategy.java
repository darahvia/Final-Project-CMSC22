import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UnscheduledEntryStrategy {
/**
 * schedules unscheduled entries based on the available time slots and units per timeslot
 * @param unscheduledEntriesQueue   queue of unscheduled entries to be scheduled
 * @param entryManager              entry manager containing scheduled and unscheduled entries
 * @param timeblockManager          time block manager for managing the available time slots
 */
    public void scheduleUnscheduledEntries(Queue<UnscheduledEntry> unscheduledEntriesQueue, EntryManager entryManager, TimeblockManager timeblockManager) {
        while (!unscheduledEntriesQueue.isEmpty()){
            List<Integer> availableSlots = timeblockManager.getAvailableSlots();
            UnscheduledEntry unscheduledEntry = unscheduledEntriesQueue.peek();

            int timeslot;
            if (availableSlots.get(0) == 0) {
                timeslot = availableSlots.get(0); 
            } else {
                timeslot = availableSlots.get(0) - 1;
            }

            int unitsPerTimeslot = unscheduledEntry.getUnitsPerTimeslot();
            int unitsRemaining = unscheduledEntry.getUnitsRemaining();

            boolean lastIteration = (unitsPerTimeslot >= unitsRemaining);

            LocalTime startTime = calculateTime(timeslot);
            LocalTime endTime = lastIteration ? calculateTime(timeslot + unitsRemaining * 4) : calculateTime(timeslot + unitsPerTimeslot * 4);

            int endSlot = calculateSlot(endTime) - 1;            // para sa occupied slots
            int endDueSlot = calculateSlot(unscheduledEntry.getDueTime());
            LocalTime endDueTime = unscheduledEntry.getDueTime();
            if (endDueSlot <= endSlot){ endSlot = endDueSlot; endTime = endDueTime;}  
            
            entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());             // add to the allEntries queue

            if (lastIteration){          // last iteration
                unscheduledEntriesQueue.poll().getName();         // no units remaining, time to dequeue
                continue;
            } else {;
                unscheduledEntry.decreaseUnits(unitsPerTimeslot);
                if (unitsRemaining > 0) {             // requeue
                    unscheduledEntriesQueue.poll();
                    entryManager.getUnscheduledEntriesQueue().add(unscheduledEntry);
                }
            }
        }
    }
    private static LocalTime calculateTime(int timeslot) {
        int hour = timeslot / 4;
        int minute = (timeslot % 4) * 15;
        return LocalTime.of(hour, minute);
    }
    public int calculateSlot (LocalTime time) {
        return time.getHour() * 4 + time.getMinute() / 15;  //returns the numeric value of the timeslot within 0 to 95
    }
}
