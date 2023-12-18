import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UnscheduledEntryStrategy {
    public void scheduleUnscheduledEntries(Queue<UnscheduledEntry> unscheduledEntriesQueue, EntryManager entryManager, TimeblockManager timeblockManager) {
        int iteration = 0;
        while (!unscheduledEntriesQueue.isEmpty()){
            iteration++;
            System.out.println("iteration " + iteration);

            List<Integer> availableSlots = timeblockManager.getAvailableSlots();
            UnscheduledEntry unscheduledEntry = unscheduledEntriesQueue.peek();
            System.out.println(entryManager.getUnscheduledEntriesQueue());

            int timeslot;
            if (availableSlots.get(0) == 0) {
                timeslot = availableSlots.get(0); 
            } else {
                timeslot = availableSlots.get(0) - 1;
            }
            int unitsPerTimeslot = unscheduledEntry.getUnitsPerTimeslot();
            int unitsRemaining = unscheduledEntry.getUnitsRemaining();

            boolean condition = (unitsPerTimeslot >= unitsRemaining);

            LocalTime startTime = calculateTime(timeslot);
            LocalTime endTime = condition ? calculateTime(timeslot + unitsRemaining * 4) : calculateTime(timeslot + unitsPerTimeslot * 4);
            entryManager.addScheduledEntry(startTime, endTime, unscheduledEntry.getName());             // add to the allEntries queue

            int startSlot = calculateMinutes(startTime);                     // update occupied timeslots
            int endSlot = calculateMinutes(endTime);

            ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
            for (int i = startSlot; i <= endSlot; i++) {
                    timeslotsToUpdate.add(i % timeblockManager.getTotalSlots());
            }
            timeblockManager.updateTimeslots(timeslotsToUpdate);  

            if (condition){          // last iteration
                System.out.println(unscheduledEntry.getName() + " last iteration");
                System.out.println("removed" + unscheduledEntriesQueue.poll().getName());         // no units remaining, time to dequeue
                continue;
            } else {
                System.out.println(unscheduledEntry.getName());
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
    public int calculateMinutes (LocalTime time) {
        return time.getHour() * 4 + time.getMinute() / 15;  //returns the numeric value of the timeslot within 0 to 95
    }
}
