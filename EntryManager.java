import java.util.Queue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * manages both the scheduled and unscheduled entries
 * @author Darah Via D. Moscoso
 * 
 */
public class EntryManager{
    private TreeMap<Integer, CalendarEntry> allEntries;
    private Queue<UnscheduledEntry> unscheduledEntriesQueue;
    TimeblockManager timeblockManager = TimeblockManager.getInstance();

    // constructor to initialize the data structure
    public EntryManager(){
        this.allEntries = new TreeMap<>();
        this.unscheduledEntriesQueue = new LinkedList<>();
    }

    // returns a TreeMap containing all entries
    public TreeMap<Integer, CalendarEntry> getAllEntries(){
        return allEntries;
    }

    // returns a Queue of unscheduled entries
    public Queue<UnscheduledEntry> getUnscheduledEntriesQueue(){
        return unscheduledEntriesQueue;
    }

    // add a scheduled entry to the calendr
    public void addScheduledEntry(LocalTime startTime, LocalTime endTime, String taskName) { 
        // calculate time slots foe start and end times
        int startSlot = calculateSlot(startTime);
        int endSlot = calculateSlot(endTime) - 1;

        // add the scheduled entry to the TreeMap for each time slot in range
        allEntries.put(startSlot, new ScheduledEntry(taskName, startTime, endTime));
        for (int i = startSlot + 1; i <= endSlot; i++) {
            allEntries.put(i, new ScheduledEntry(taskName, startTime, endTime));
        }
        
        // update time block manager with the occupied time slots
        ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
        for (int i = startSlot; i <= endSlot; i++) {
            timeslotsToUpdate.add(i);
        }

        timeblockManager.updateTimeslots(timeslotsToUpdate);
        System.out.println(timeblockManager.getAvailableSlots());        
    }

    //returns the numeric value of the timeslot within 0 to 95
    public int calculateSlot (LocalTime time) {
        return time.getHour() * 4 + time.getMinute() / 15;  
    }
}