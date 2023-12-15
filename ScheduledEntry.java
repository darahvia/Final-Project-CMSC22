import java.time.LocalTime;

public class ScheduledEntry extends CalendarEntry {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;


    public ScheduledEntry(String name, LocalTime startTime, LocalTime endTime) {
        super(name);
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