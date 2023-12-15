import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

public class UnscheduledEntryStrategy {
    public void scheduleUnscheduledEntries(Queue<UnscheduledEntry> unscheduledEntriesQueue, EntryManager entryManager, TimeblockManager timeblockManager) {

        ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
        Iterator<UnscheduledEntry> iterator = unscheduledEntriesQueue.iterator();
        
        while (iterator.hasNext()) {                // loop the unscheduled entries queue
            UnscheduledEntry unscheduledEntry = iterator.next();

            if (unscheduledEntry.getUnitsRemaining() == 0) {
                iterator.remove();
                continue;
            }
            
            int unitsPerTimeslot = unscheduledEntry.getUnitsPerTimeslot();
            List<Integer> availableSlots = timeblockManager.getAvailableSlots();

            if (!availableSlots.isEmpty()){
                int timeslot = availableSlots.get(0);
                LocalTime startTime = calculateTime(timeslot);
                LocalTime endTime = calculateTime(timeslot + unitsPerTimeslot * 4);

                for (int i = timeslot; i < timeslot + unitsPerTimeslot * 4; i++) {
                 timeslotsToUpdate.add(i);
                }
                
                entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());
            }
            }
        }

        private static LocalTime calculateTime(int timeslot) {
            int hour = timeslot / 4;
            int minute = (timeslot % 4) * 15;
            return LocalTime.of(hour, minute);
        }


}
