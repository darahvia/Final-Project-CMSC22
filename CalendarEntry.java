import java.time.LocalTime;

abstract class CalendarEntry {
    private String name;

    public CalendarEntry(String name){
        this.name = name;
    }

    public String getName() { 
        return name;
    }

    public abstract LocalTime getStartTime();

    public abstract LocalTime getEndTime();
}