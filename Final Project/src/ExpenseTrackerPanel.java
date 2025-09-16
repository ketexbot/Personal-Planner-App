import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ExpenseTrackerPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private double totalBalance = 0.0;
    private double totalEarnings = 0.0;
    private double totalSpendings = 0.0;
    private JLabel earningsLabel;
    private JLabel spendingsLabel;

    public ExpenseTrackerPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        // Header panel with Back button and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 30, 30)); // Dark header background

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(70, 70, 70)); // Dark gray button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        headerPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Expense Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Table to display earnings and spendings
        String[] columnNames = {"Type", "Description", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable expenseTable = new JTable(tableModel);
        expenseTable.setBackground(new Color(45, 45, 45)); // Dark table background
        expenseTable.setForeground(Color.LIGHT_GRAY);
        expenseTable.setSelectionBackground(new Color(70, 130, 180)); // Steel blue for selection
        expenseTable.setSelectionForeground(Color.WHITE);
        JScrollPane tableScrollPane = new JScrollPane(expenseTable);
        tableScrollPane.getViewport().setBackground(new Color(45, 45, 45)); // Match table background
        add(tableScrollPane, BorderLayout.CENTER);

        // Input panel for adding earnings or spendings
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        inputPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setForeground(Color.LIGHT_GRAY);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(typeLabel, gbc);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Earning", "Spending"});
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        typeBox.setBackground(new Color(45, 45, 45));
        typeBox.setForeground(Color.LIGHT_GRAY);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(typeBox, gbc);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setForeground(Color.LIGHT_GRAY);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(descLabel, gbc);

        JTextField descField = new JTextField();
        descField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descField.setBackground(new Color(45, 45, 45));
        descField.setForeground(Color.LIGHT_GRAY);
        descField.setCaretColor(new Color(70, 130, 180));
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(descField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.LIGHT_GRAY);
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);

        JTextField amountField = new JTextField();
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        amountField.setBackground(new Color(45, 45, 45));
        amountField.setForeground(Color.LIGHT_GRAY);
        amountField.setCaretColor(new Color(70, 130, 180));
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(amountField, gbc);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        inputPanel.add(addButton, gbc);

        add(inputPanel, BorderLayout.EAST);

        // Add event logic
        addButton.addActionListener(e -> {
            String type = (String) typeBox.getSelectedItem();
            String desc = descField.getText().trim();
            String amtStr = amountField.getText().trim();
            if (desc.isEmpty() || amtStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter description and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amt;
            try {
                amt = Double.parseDouble(amtStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (type.equals("Spending")) {
                amt = -amt;
                totalSpendings += Math.abs(amt);
            } else {
                totalEarnings += amt;
            }
            tableModel.addRow(new Object[]{type, desc, String.format("%.2f", Math.abs(amt))});
            totalBalance += amt;
            updateTotalLabel();
            descField.setText("");
            amountField.setText("");
        });

        // Custom cell renderer for coloring rows
        expenseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                String type = table.getValueAt(row, 0) != null ? table.getValueAt(row, 0).toString() : "";
                if (type.equals("Earning")) {
                    label.setForeground(new Color(0, 200, 0));
                } else if (type.equals("Spending")) {
                    label.setForeground(new Color(220, 50, 50));
                } else {
                    label.setForeground(Color.LIGHT_GRAY);
                }
                label.setBackground(new Color(45, 45, 45));
                if (isSelected) label.setBackground(new Color(70, 130, 180));
                label.setOpaque(true);
                return label;
            }
        });

        // Footer panel for total balance and difference
        JPanel footerPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        footerPanel.setBackground(new Color(30, 30, 30));
        earningsLabel = new JLabel("Total Earnings: $0.00");
        earningsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        earningsLabel.setForeground(new Color(0, 200, 0));
        footerPanel.add(earningsLabel);
        spendingsLabel = new JLabel("Total Spendings: $0.00");
        spendingsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        spendingsLabel.setForeground(new Color(220, 50, 50));
        footerPanel.add(spendingsLabel);
        totalLabel = new JLabel("Net Balance: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(Color.LIGHT_GRAY);
        footerPanel.add(totalLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void updateTotalLabel() {
        earningsLabel.setText(String.format("Total Earnings: $%.2f", totalEarnings));
        spendingsLabel.setText(String.format("Total Spendings: $%.2f", totalSpendings));
        totalLabel.setText(String.format("Net Balance: $%.2f", totalBalance));
    }
}
