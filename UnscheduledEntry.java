
import java.time.LocalTime;
class UnscheduledEntry extends CalendarEntry {
    private String name;
    private String dueTime;
    private int units;
    private int unitsPerTimeslot;
    private int unitsRemaining;

    public UnscheduledEntry(String name, String dueTime, int units, int unitsPerTimeslot) {
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

    public String getDueTime() {
        return dueTime;
    }

    public int getUnits() {
        return units;
    }

    public int getUnitsPerTimeslot(){
        return unitsPerTimeslot;
    }

    public void decreaseUnits(int subtractUnits){
        this.unitsRemaining -= subtractUnits;
    }
    
    public int getUnitsRemaining(){
        return unitsRemaining;
    }

    
}