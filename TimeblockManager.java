import java.time.LocalTime;
import java.util.*;

/**
 * manages time blocks and availability of time slots in a day
 */
 class TimeblockManager {
    private static TimeblockManager instance = null;
    private TreeMap<Integer, String> allEntries;

    /**
     * private constructor to initialize the TimeblockManager instance
     * @return
     */
    public TimeblockManager() {
        allEntries = new TreeMap<>();   // maps time slots to corresponging entry names
    }

    final static int MAX_TIME_SLOTS = 24 * 4;   // max number of time slots in a day (24 hours * 4 time slots per hour)
    boolean[] occupiedTimeslots = new boolean[MAX_TIME_SLOTS];  // tracks occupation status of each timeslot

    /**
     * retrives singleton instance of TimeblockManager
     * @return the TimeblockManager instance
     */
    public static TimeblockManager getInstance() {
        if (instance == null){
            instance = new TimeblockManager();
        }
        return instance;
    }

    /**
     * 
     * @param timeslotsToUpdate the list of time slots to be updated
     */
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

    public TreeMap<Integer, String> getAllEntries() {
            return allEntries;
    }
}
