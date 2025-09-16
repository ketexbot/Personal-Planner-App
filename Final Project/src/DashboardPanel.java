import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Gradient background for the dashboard
        setBackground(Color.DARK_GRAY);

        // Menu panel on the left
        JPanel panelMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(45, 45, 45), 0, getHeight(), new Color(30, 30, 30));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        // Create buttons
        JButton buttonSchedule = createMenuButton("Schedule", cardLayout, cardPanel, "Schedule");
        JButton buttonTodo = createMenuButton("To-Do", cardLayout, cardPanel, "To Do");
        JButton buttonExpenseTracker = createMenuButton("Expense Tracker", cardLayout, cardPanel, "Expense Tracker");
        JButton buttonCalendar = createMenuButton("Calendar", cardLayout, cardPanel, "Calendar");

        // Add buttons to the menu panel
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(buttonSchedule);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        panelMenu.add(buttonTodo);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        panelMenu.add(buttonExpenseTracker);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        panelMenu.add(buttonCalendar);
        panelMenu.add(Box.createVerticalGlue());

        // Add the menu panel to the left side of the dashboard
        add(panelMenu, BorderLayout.WEST);

        // Main content area
        JPanel panelMainContent = new JPanel();
        panelMainContent.setBackground(new Color(50, 50, 50)); // Dark gray background
        add(panelMainContent, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, CardLayout cardLayout, JPanel cardPanel, String targetPanel) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 50));
        button.setMaximumSize(new Dimension(180, 50));
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 70, 70)); // Dark gray button
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to the button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 100, 100)); // Lighter gray on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70)); // Original dark gray
            }
        });

        // Addaction listener to navigate to the target panel
        button.addActionListener(e -> cardLayout.show(cardPanel, targetPanel));

        return button;
    }
}
