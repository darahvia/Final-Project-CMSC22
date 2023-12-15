import java.util.Queue;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class EntryManager{
    private TreeMap<Integer, Entry> allEntries;
    private Queue<UnscheduledEntry> unscheduledEntriesQueue;
    TimeblockManager timeblockManager = new TimeblockManager();

    public EntryManager(){
        this.allEntries = new TreeMap<>();
        this.unscheduledEntriesQueue = new LinkedList<>();
    }

    public TreeMap<Integer, Entry> getAllEntries(){
        return allEntries;
    }

    public Queue<UnscheduledEntry> getUnscheduledEntriesQueue(){
        return unscheduledEntriesQueue;
    }

    public void addScheduledEntry(int startMinutes, ScheduledEntry scheduledEntry, int endMinutes) { 
        allEntries.put(startMinutes, scheduledEntry);
        allEntries.put(endMinutes, null);

        ArrayList<Integer> timeslotsToUpdate = new ArrayList<>();
        for (int i = startMinutes; i <= endMinutes; i++) {
            timeslotsToUpdate.add(i);
        }
    }
}