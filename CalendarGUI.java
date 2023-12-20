import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;
import java.time.format.DateTimeParseException;


public class CalendarGUI extends JFrame {
    private EntryManager entryManager;
    private JButton displayEntriesButton;
    private JButton addScheduledEntryButton;
    private JButton addUnscheduledEntryButton;
    // private JTextArea entriesTextArea;
    private JTable entriesTable;

    public CalendarGUI() {
        // Set the title
        setTitle("MySec");

        // Set the icon (logo)
        ImageIcon icon = new ImageIcon("mysec_logo.png");
        setIconImage(icon.getImage());

        // Initialize components
        entryManager = new EntryManager();

        displayEntriesButton = new JButton("Display All Entries");
        addScheduledEntryButton = new JButton("Add Scheduled Entry");
        addUnscheduledEntryButton = new JButton("Add Unscheduled Entry");
        // entriesTextArea = new JTextArea(20, 40);

        // Set layout and add components
        setLayout(new FlowLayout());
        add(displayEntriesButton);
        add(addScheduledEntryButton);
        add(addUnscheduledEntryButton);
        // add(entriesTextArea);

        // Set up event handling
        displayEntriesButton.addActionListener(e -> displayAllEntries());
        addScheduledEntryButton.addActionListener(e -> addScheduledEntry());
        addUnscheduledEntryButton.addActionListener(e -> addUnscheduledEntry());

        // Set up table
        entriesTable = new JTable();
        entriesTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(entriesTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        add(scrollPane);
        // Set frame properties
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void displayAllEntries() {
        TreeMap<Integer, CalendarEntry> allEntries = entryManager.getAllEntries();
        // Assuming MAX_TIME_SLOTS is a constant in your TimeblockManager class
        int maxTimeSlots = TimeblockManager.MAX_TIME_SLOTS;
    
        // Create a DefaultTableModel with columns "Time Slot", "Time Block", "Status", "Task Name"
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Time Slot", "Time Block", "Status", "Task Name"}, 0);
    
        for (int i = 0; i < maxTimeSlots; i++) {
            int hour = i / 4;
            int minute = (i % 4) * 15;
    
            String timeBlock = String.format("%02d:%02d", hour, minute);
    
            Map.Entry<Integer, CalendarEntry> floorEntry = allEntries.floorEntry(i);
            String taskName = (floorEntry != null && floorEntry.getValue() != null) ? floorEntry.getValue().getName() : "N/A";
            String status = (floorEntry != null && floorEntry.getValue() != null) ? "Occupied" : "Available";
    
            // Add a row to the model
            model.addRow(new Object[]{i, timeBlock, status, taskName});
        }
    
        // Set the model for the JTable
        entriesTable.setModel(model);
        setCellColors();
    }

    private void setCellColors() {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Check if the cell represents an occupied time slot
                    String status = table.getValueAt(row, 2).toString();
                    if ("Occupied".equals(status)) {
                        c.setBackground(Color.decode("#B0FC38")); // Set the color for occupied slots
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(table.getBackground());
                        c.setForeground(table.getForeground());
                    }

                    return c;
                }
            };

            for (int i = 0; i < entriesTable.getColumnCount(); i++) {
                entriesTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }

    private String calculateTime(int timeslot) {
        int hour = timeslot / 4;
        int minute = (timeslot % 4) * 15;

        return String.format("%02d:%02d", hour, minute);
    }

    private void addScheduledEntry() {
        // Prompt the user for scheduled entry details
        String taskName = JOptionPane.showInputDialog("Enter Task Name for Scheduled Entry:");
        String startTimeStr = JOptionPane.showInputDialog("Enter Start Time (HH:mm) for Scheduled Entry:");
        String endTimeStr = JOptionPane.showInputDialog("Enter End Time (HH:mm) for Scheduled Entry");

        try {
            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            if (startTime.isAfter(endTime)) {
                JOptionPane.showMessageDialog(this, "Error: Invalid start/end time", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add to EntryManager by passing the required parameters
            entryManager.addScheduledEntry(startTime, endTime, taskName);

            JOptionPane.showMessageDialog(this, "Scheduled Entry Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Error: Invalid start/end time", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void addUnscheduledEntry() {
        // Prompt the user for unscheduled entry details
        String taskName = JOptionPane.showInputDialog("Enter Task Name for Unscheduled Entry:");
        String dueTimeStr = JOptionPane.showInputDialog("Enter Due Time (HH:mm) for Unscheduled Entry:");
        String unitsStr = JOptionPane.showInputDialog("Enter Number of Units for Unscheduled Entry:");
        String unitsPerSlotStr = JOptionPane.showInputDialog("Enter Units Per Slot for Unscheduled Entry:");

    try {
        // Correct import statement for LocalTime
        java.time.LocalTime dueTime = java.time.LocalTime.parse(dueTimeStr);
        int units = Integer.parseInt(unitsStr);
        int unitsPerSlot = Integer.parseInt(unitsPerSlotStr);

        // Add to EntryManager
        entryManager.getUnscheduledEntriesQueue().add(new UnscheduledEntry(taskName, dueTime, units, unitsPerSlot));

        JOptionPane.showMessageDialog(this, "Unscheduled Entry Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

    } catch (java.time.format.DateTimeParseException | NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error: Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalendarGUI calendarGUI = new CalendarGUI();
            calendarGUI.setVisible(true);
        });
    }
}
