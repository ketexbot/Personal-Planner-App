import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(CardLayout cardLayout, JPanel cardPanel) {
        //to set a gradient background
        setLayout(new GridBagLayout());

        // Welcome label
        JLabel labelWelcome = new JLabel("Welcome");
        labelWelcome.setFont(new Font("Segoe UI", Font.BOLD, 48));
        labelWelcome.setForeground(Color.LIGHT_GRAY);

        // Modern button with rounded corners
        JButton buttonEnter = new JButton("Enter");
        buttonEnter.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        buttonEnter.setForeground(Color.WHITE);
        buttonEnter.setBackground(new Color(0, 123, 255)); // Blue button
        buttonEnter.setFocusPainted(false);
        buttonEnter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonEnter.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect to the button
        buttonEnter.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonEnter.setBackground(new Color(0, 150, 255)); // Light blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonEnter.setBackground(new Color(0, 123, 255)); // normal color 
            }
        });

        // Addaction listener to go to the dashboard
        buttonEnter.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));

        // Layout 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(labelWelcome, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(buttonEnter, gbc);
    }

    // Override paintComponent in the WelcomePanel class
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 30, 30), getWidth(), getHeight(), new Color(50, 50, 50));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
