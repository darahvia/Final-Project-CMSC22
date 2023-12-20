import java.time.LocalTime;
/**
 * abstract class that represents a calendar entry
 * @author Darah Via Moscoso
 */
abstract class CalendarEntry {
    private String name;

    public CalendarEntry(String name){
        this.name = name;
    }

    public String getName() { 
        return name;
    }

/**
 * abstract method to get the start time of a calendar entry
 * subclasses must implement this method
 * @return the start
 */
    public abstract LocalTime getStartTime();
    public abstract LocalTime getEndTime();
}