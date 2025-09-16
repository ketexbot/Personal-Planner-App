import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class CalendarPanel extends JPanel {
    private final Map<LocalDate, String> events = new HashMap<>(); // Store events for dates
    private LocalDate currentDate = LocalDate.now(); // Current month and year

    public CalendarPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Header panel with Back button and month label
        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(monthLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Calendar grid panel
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7)); // 7 columns for days of the week
        add(calendarGrid, BorderLayout.CENTER);

        // panel to change months
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        footerPanel.add(prevButton);
        footerPanel.add(nextButton);
        add(footerPanel, BorderLayout.SOUTH);

        // update the calendar
        updateCalendar(calendarGrid, monthLabel);

        // button actions
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar(calendarGrid, monthLabel);
        });

        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar(calendarGrid, monthLabel);
        });
    }

    private void updateCalendar(JPanel calendarGrid, JLabel monthLabel) {
        calendarGrid.removeAll(); // Clear the grid

        // Set the month label
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        monthLabel.setText(yearMonth.getMonth() + " " + yearMonth.getYear());

        // Add day-of-week headers
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));
            calendarGrid.add(dayLabel);
        }

        // Calculate the first day of the month and the number of days in the month
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue() % 7; // Adjust for Sunday start
        int daysInMonth = yearMonth.lengthOfMonth();

        // Add empty buttons for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            calendarGrid.add(new JLabel("")); 
        }

        // Add buttons for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("Arial", Font.PLAIN, 16));
            dayButton.addActionListener(e -> setEventForDate(date)); // Add event on click
            calendarGrid.add(dayButton);
        }

        // Refresh the calendar
        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private void setEventForDate(LocalDate date) {
        String currentEvent = events.getOrDefault(date, "");
        String newEvent = JOptionPane.showInputDialog(this, "Set Event for " + date + ":", currentEvent);
        if (newEvent != null && !newEvent.trim().isEmpty()) {
            events.put(date, newEvent); // Save the event
        } else if (newEvent != null) {
            events.remove(date); // Remove the event if the input is empty
        }
    }
}
