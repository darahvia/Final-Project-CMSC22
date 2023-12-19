import java.util.Queue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class EntryManager{
    private TreeMap<Integer, CalendarEntry> allEntries;
    private Queue<UnscheduledEntry> unscheduledEntriesQueue;
    TimeblockManager timeblockManager = TimeblockManager.getInstance();

    public EntryManager(){
        this.allEntries = new TreeMap<>();
        this.unscheduledEntriesQueue = new LinkedList<>();
    }

    public TreeMap<Integer, CalendarEntry> getAllEntries(){
        return allEntries;
    }

    public Queue<UnscheduledEntry> getUnscheduledEntriesQueue(){
        return unscheduledEntriesQueue;
    }

    public void addScheduledEntry(LocalTime startTime, LocalTime endTime, String taskName) { 
        int startSlot = calculateMinutes(startTime);
        int endSlot = calculateMinutes(endTime);

        allEntries.put(startSlot, new ScheduledEntry(taskName, startTime, endTime));
        allEntries.put(endSlot, null);

        ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
        for (int i = startSlot; i <= endSlot; i++) {
            timeslotsToUpdate.add(i);
        }

        timeblockManager.updateTimeslots(timeslotsToUpdate);
    }

    public int calculateMinutes (LocalTime time) {
        return time.getHour() * 4 + time.getMinute() / 15;  //returns the numeric value of the timeslot within 0 to 95
    }
}