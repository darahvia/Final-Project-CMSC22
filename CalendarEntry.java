import java.time.LocalTime;
/**
 * abstract class that represents a calendar entry
 */
abstract class CalendarEntry {
    private String name;
/**
 * constructor for creating a Calendar entry given the task name
 * @param name
 */
    public CalendarEntry(String name){
        this.name = name;
    }
/**
 * gets the name of the calendar entry
 * @return the name of the calendar entry
 */
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