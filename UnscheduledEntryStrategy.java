import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

public class UnscheduledEntryStrategy {
    public void scheduleUnscheduledEntries(Queue<UnscheduledEntry> unscheduledEntriesQueue, EntryManager entryManager, TimeblockManager timeblockManager) {

        
        while (!unscheduledEntriesQueue.isEmpty()){
            List<Integer> availableSlots = timeblockManager.getAvailableSlots();
            UnscheduledEntry unscheduledEntry = unscheduledEntriesQueue.peek();
            int units = unscheduledEntry.getUnits();

            int unitsPerTimeslot = unscheduledEntry.getUnitsPerTimeslot();

            if (unitsPerTimeslot >= unscheduledEntry.getUnitsRemaining()){          // last iteration
                int timeslot = availableSlots.get(0);
                LocalTime startTime = calculateTime(timeslot);
                LocalTime endTime = calculateTime(timeslot + unscheduledEntry.getUnitsRemaining() * 4);     //duration

                int startSlot = calculateMinutes(startTime);
                int endSlot = calculateMinutes(endTime);

                // to update the occupied timeslots
                ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
                for (int i = startSlot; i <= endSlot; i++) {
                    timeslotsToUpdate.add(i % timeblockManager.getTotalSlots());
                }
                timeblockManager.updateTimeslots(timeslotsToUpdate);

                entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());
                unscheduledEntriesQueue.poll();
                
            } else {
                int timeslot = availableSlots.get(0);
                LocalTime startTime = calculateTime(timeslot);
                LocalTime endTime = calculateTime(timeslot + unitsPerTimeslot * 4);     //duration

                int startSlot = calculateMinutes(startTime);
                int endSlot = calculateMinutes(endTime);
                
                
                // to update the occupied timeslots
                ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
                for (int i = startSlot; i <= endSlot; i++) {
                    timeslotsToUpdate.add(i % timeblockManager.getTotalSlots());
                }
                timeblockManager.updateTimeslots(timeslotsToUpdate);


                entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());
                unscheduledEntry.decreaseUnits(unitsPerTimeslot);

                if (unscheduledEntry.getUnitsRemaining() > 0) {             // requeue
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
        public int calculateMinutes (LocalTime time) {
            return time.getHour() * 4 + time.getMinute() / 15;  //returns the numeric value of the timeslot within 0 to 95
        }
    

}
