import java.time.LocalTime;
/**
 * represents the unscheduled calendar entry that needs to be assigned a timeslot.
 * @author Darah Via D. Moscoso 
 * @author Khean Maree C. Vallejara
 */
class UnscheduledEntry extends CalendarEntry {
    private String name;
    private LocalTime dueTime;
    private int units;              
    private int unitsPerTimeslot;   
    private int unitsRemaining;    
    /**
     * constructor for creating an UnscheduledEntry object
     * 
     * @param name                  name of unscheduled entry
     * @param dueTime               the time to user wants the entry to finish 
     * @param units                 1 units = 1 hour, the duration in which the user thinks they finish the entry
     * @param unitsPerTimeslot      this will be use to the schedulilng of the unscheduled entries
     */
    public UnscheduledEntry(String name, LocalTime dueTime, int units, int unitsPerTimeslot) {
        super(name);
        this.name = name;
        this.dueTime = dueTime;
        this.units = units;
        this.unitsPerTimeslot = unitsPerTimeslot;
        this.unitsRemaining = units;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalTime getStartTime() {
        return null;
    }

    public LocalTime getEndTime() {
        return null;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public int getUnits() {
        return units;
    }

    public int getUnitsPerTimeslot(){
        return unitsPerTimeslot;
    }
/**
 * 
 * @param subtractUnits the amount to subtract from the remaining units
 */
    public void decreaseUnits(int subtractUnits){
        this.unitsRemaining -= subtractUnits;
    }
    
    public int getUnitsRemaining(){
        return unitsRemaining;
    }
}