import java.time.LocalTime;

public class ScheduledEntry implements Entry {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduledEntry(String name, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ScheduledEntry{" +
                "name='" + name + '\'' +
                ", day='" + startTime + '\'' +
                ", time='" + endTime + '\'' +
                '}';
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}