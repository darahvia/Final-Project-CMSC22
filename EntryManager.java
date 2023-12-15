import java.util.Queue;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class EntryManager{
    private TreeMap<Integer, CalendarEntry> allEntries;
    private Queue<UnscheduledEntry> unscheduledEntriesQueue;
    TimeblockManager timeblockManager = new TimeblockManager();

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
        int startMinutes = calculateMinutes(startTime);
        int endMinutes = calculateMinutes(endTime);

        allEntries.put(startMinutes, new ScheduledEntry(taskName, startTime, endTime));
        allEntries.put(endMinutes, null);

        ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
        for (int i = startMinutes; i <= endMinutes; i++) {
            timeslotsToUpdate.add(i);
        }

        timeblockManager.updateTimeslots(timeslotsToUpdate);
    }


    public int calculateMinutes (LocalTime time) {
        return time.getHour() * 4 + time.getMinute();
    }
}