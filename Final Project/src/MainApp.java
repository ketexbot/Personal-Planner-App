/*
 * Personal Planner app
 * Kunga Lama Tamang
 * 
 */


import javax.swing.*;
import java.awt.*;
public class MainApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        //add panels to the card layout
        cardPanel.add(new WelcomePanel(cardLayout, cardPanel), "Welcome Screen");
        cardPanel.add(new DashboardPanel(cardLayout, cardPanel), "Dashboard");
        cardPanel.add(new TodoPanel(cardLayout, cardPanel), "To Do");
        cardPanel.add(new SchedulePanel(cardLayout, cardPanel), "Schedule");
        cardPanel.add(new ExpenseTrackerPanel(cardLayout, cardPanel), "Expense Tracker");
        cardPanel.add(new CalendarPanel(cardLayout, cardPanel), "Calendar");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}