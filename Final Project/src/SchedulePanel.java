import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SchedulePanel extends JPanel {
    private DefaultTableModel tableModel;

    public SchedulePanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Header panel with Back button and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 25, 35));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        // Hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(100, 160, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(70, 130, 180));
            }
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Weekly Schedule", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(220, 220, 230));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        tableModel = new DefaultTableModel(columnNames, 24) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        JTable scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(32);
        scheduleTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        scheduleTable.getTableHeader().setBackground(new Color(40, 40, 60));
        scheduleTable.getTableHeader().setForeground(new Color(180, 200, 220));
        scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        scheduleTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        scheduleTable.setShowGrid(true);
        scheduleTable.setGridColor(new Color(70, 130, 180));

        // Fill time column
        for (int i = 0; i < 24; i++) {
            tableModel.setValueAt(String.format("%02d:00", i), i, 0);
        }

        // Custom cell renderer for event highlighting
        scheduleTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                label.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                if (column == 0) {
                    label.setBackground(new Color(45, 45, 60));
                    label.setForeground(new Color(180, 200, 220));
                } else if (value != null && !value.toString().isEmpty()) {
                    label.setBackground(new Color(70, 130, 180));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(35, 35, 45));
                    label.setForeground(new Color(120, 130, 140));
                }
                if (isSelected) {
                    label.setBackground(new Color(100, 160, 210));
                }
                label.setOpaque(true);
                return label;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        add(tableScrollPane, BorderLayout.CENTER);

        // Input panel for adding events
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(25, 25, 35));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setForeground(new Color(180, 200, 220));
        dayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(dayLabel, gbc);

        JComboBox<String> dayBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        dayBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dayBox.setBackground(new Color(40, 40, 60));
        dayBox.setForeground(new Color(180, 200, 220));
        dayBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(dayBox, gbc);

        JLabel startLabel = new JLabel("Start:");
        startLabel.setForeground(new Color(180, 200, 220));
        startLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(startLabel, gbc);

        JComboBox<String> startTimeBox = new JComboBox<>();
        JComboBox<String> endTimeBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            String time = String.format("%02d:00", i);
            startTimeBox.addItem(time);
            endTimeBox.addItem(time);
        }
        startTimeBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        startTimeBox.setBackground(new Color(40, 40, 60));
        startTimeBox.setForeground(new Color(180, 200, 220));
        startTimeBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        gbc.gridx = 3; gbc.gridy = 0;
        inputPanel.add(startTimeBox, gbc);

        JLabel endLabel = new JLabel("End:");
        endLabel.setForeground(new Color(180, 200, 220));
        endLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(endLabel, gbc);

        endTimeBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        endTimeBox.setBackground(new Color(40, 40, 60));
        endTimeBox.setForeground(new Color(180, 200, 220));
        endTimeBox.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(endTimeBox, gbc);

        JLabel eventLabel = new JLabel("Event:");
        eventLabel.setForeground(new Color(180, 200, 220));
        eventLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(eventLabel, gbc);

        JTextField eventField = new JTextField();
        eventField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        eventField.setBackground(new Color(40, 40, 60));
        eventField.setForeground(new Color(180, 200, 220));
        eventField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1, true));
        eventField.setCaretColor(new Color(70, 130, 180));
        gbc.gridx = 3; gbc.gridy = 1;
        inputPanel.add(eventField, gbc);

        JButton addButton = new JButton("Add Event");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hover effect
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(100, 160, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(70, 130, 180));
            }
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        // Add event logic
        addButton.addActionListener(e -> {
            int dayCol = dayBox.getSelectedIndex() + 1;
            int startRow = startTimeBox.getSelectedIndex();
            int endRow = endTimeBox.getSelectedIndex();
            String event = eventField.getText().trim();
            if (event.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an event.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (endRow <= startRow) {
                JOptionPane.showMessageDialog(this, "End time must be after start time.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int r = startRow; r < endRow; r++) {
                tableModel.setValueAt(event, r, dayCol);
            }
            eventField.setText("");
        });
    }
}
