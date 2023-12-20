import java.time.LocalTime;

/**
 * represents the scheduled entry in a calendar
 */
public class ScheduledEntry extends CalendarEntry {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

/**
 * constructor for creating a scheduled entry
 * 
 * @param name          name of scheduled entry
 * @param startTime     start time of scheduled entry
 * @param endTime       end time of scheduled entry
 */
    public ScheduledEntry(String name, LocalTime startTime, LocalTime endTime) {
        super(name);            // call the constructor of the super class CalendarEntry
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }
}