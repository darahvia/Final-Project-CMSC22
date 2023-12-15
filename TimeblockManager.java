import java.time.LocalTime;
import java.util.*;

 class TimeblockManager {
    private TreeMap<Integer, String> allEntries;

    public TimeblockManager() {
        allEntries = new TreeMap<>();
    }

    final static int MAX_TIME_SLOTS = 24 * 4; 
    boolean[] occupiedTimeslots = new boolean[MAX_TIME_SLOTS];;


    public void addTimeBlock(LocalTime startTime, LocalTime endTime, String entryName) {
        int startMinutes = startTime.getHour() * 4 + startTime.getMinute();
        int endMinutes = endTime.getHour() * 4 + endTime.getMinute();

        allEntries.put(startMinutes, entryName);
        for (int i = startMinutes + 1; i < endMinutes; i++) {
            allEntries.put(i, entryName); // Mark the time slots between start and end as occupied
        }
        allEntries.put(endMinutes, null);
    }

    public void updateTimeslots(ArrayList<Integer> timeslotsToUpdate) {
        for (int timeslot : timeslotsToUpdate){
            occupiedTimeslots[timeslot] = true;
        }
    }

    public boolean isTimeslotOccupied(int timeslot) {
        return occupiedTimeslots[timeslot];
    }

    public List<Integer> getAvailableSlots() {
        List<Integer> availableSlots = new ArrayList<>();
        for (int i = 0; i < MAX_TIME_SLOTS; i++) {
            if (!isTimeslotOccupied(i)) {
                availableSlots.add(i);
            }
        }
        return availableSlots;
    }


    public void displayOccupiedTimeslots() {
        System.out.println("Occupied Time Slots:");
        System.out.println(Arrays.toString(occupiedTimeslots));
    }

    public TreeMap<Integer, String> getAllEntries() {
            return allEntries;
    }
}
