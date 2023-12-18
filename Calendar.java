import java.util.Scanner;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;
import java.util.InputMismatchException;

public class Calendar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TimeblockManager timeblockManager = TimeblockManager.getInstance();     //singleton because it will be used for the unscheduled
        EntryManager entryManager = new EntryManager();

        // scheduled entries
        System.out.println("What are your scheduled agendas today?");
        while (true) {
            System.out.print("\nEnter Scheduled Task Name (done to finish): ");
            String taskName = scanner.nextLine();

            if (taskName.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter start time (HH:mm): ");
            String startTimeStr = scanner.nextLine();
            System.out.print("Enter end time (HH:mm): ");
            String endTimeStr = scanner.nextLine();

            try {     // checks for valid input for start and end time
                LocalTime startTime = LocalTime.parse(startTimeStr);
                LocalTime endTime = LocalTime.parse(endTimeStr);

                if (startTime.isAfter(endTime)) {    // invalid input if start time is later than end time
                    System.out.println("Error: Invalid start/end time");
                    continue;
                }

                int startSlot = entryManager.calculateMinutes(startTime);
                int endSlot = entryManager.calculateMinutes(endTime);

                if (timeblockManager.isTimeslotOccupied(startSlot) || timeblockManager.isTimeslotOccupied(endSlot)) {    // invalid input if timeslot is occupied
                    System.out.println("Error: Timeslots are occupied");
                    continue;
                }

                entryManager.addScheduledEntry(startTime, endTime, taskName);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid start/end time");
            }
        }

        // unscheduled entries
        System.out.println("\nWhat are your unscheduled agendas today?");

        while (true) {
            System.out.print("\nEnter Unscheduled Task Name (done to finish): ");
            String taskName = scanner.nextLine();

            if (taskName.equalsIgnoreCase("done")) {
                break;
            }
            
            try {       // checks for valid due time
                System.out.print("Enter due time (HH:mm): ");
                String dueTimeStr = scanner.nextLine();
                LocalTime dueTime = LocalTime.parse(dueTimeStr);

                int units = 0;
                int unitsPerTimeslot = 0;

                try {     // checks for valid units and units per timeslot
                    System.out.print("Enter units: ");
                    units = scanner.nextInt();
                    System.out.print("Enter units per timeslot: ");
                    unitsPerTimeslot = scanner.nextInt();
                    scanner.nextLine();

                    if (timeblockManager.getAvailableSlots().size() < units) {      // check if there are enough avaliable timeslots
                        System.out.println("Error: Available timeslots not enough");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Error: Invalid units");
                    continue;
                }

                entryManager.getUnscheduledEntriesQueue().add(new UnscheduledEntry(taskName, dueTime, units, unitsPerTimeslot));
            } catch (DateTimeParseException e) {
                scanner.nextLine();
                System.out.println("Error: Invalid due time");
            }
        }

        UnscheduledEntryStrategy unscheduledEntryStrategy = new UnscheduledEntryStrategy();
        unscheduledEntryStrategy.scheduleUnscheduledEntries(entryManager.getUnscheduledEntriesQueue(), entryManager, timeblockManager);

        displayAllEntries(entryManager.getAllEntries());
        scanner.close();
    }

    private static void displayAllEntries(TreeMap<Integer, CalendarEntry> allEntries) {
        System.out.println("\nAll Entries:");
    
        for (int i = 0; i < TimeblockManager.MAX_TIME_SLOTS; i++) {
            int hour = i / 4;
            int minute = (i % 4) * 15;
    
            String timeBlock = String.format("%02d:%02d", hour, minute);
    
            Map.Entry<Integer, CalendarEntry> floorEntry = allEntries.floorEntry(i);
            String taskName = (floorEntry != null && floorEntry.getValue() != null) ? floorEntry.getValue().getName() : "N/A";
            String status = (floorEntry != null && floorEntry.getValue() != null) ? "Occupied" : "Available";
    
            System.out.println("Time Slot " + i + " (" + timeBlock + "): " + status + " - Task: " + taskName);
        }
    }
}

