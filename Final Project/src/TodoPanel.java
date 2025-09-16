import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TodoPanel extends JPanel {
    private static final String TASKS_FILE = "tasks.txt"; // File to save tasks

    public TodoPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Dark background

        // Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(45, 45, 45)); // Slightly lighter dark background
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(70, 70, 70)); // Dark gray button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        panelHeader.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("To-Do List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.LIGHT_GRAY);
        panelHeader.add(titleLabel, BorderLayout.CENTER);

        add(panelHeader, BorderLayout.NORTH);

        // Task list
        DefaultListModel<String> taskListModel = new DefaultListModel<>();
        JList<String> taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        taskList.setBackground(new Color(45, 45, 45)); // Dark background for task list
        taskList.setForeground(Color.LIGHT_GRAY);
        taskList.setSelectionBackground(new Color(70, 130, 180)); // blue for selection
        taskList.setSelectionForeground(Color.WHITE);

        Map<String, Color> taskColors = new HashMap<>();
        taskList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String task = value.toString();

                if (task.endsWith(" (Done)")) {
                    Map<TextAttribute, Object> attributes = new HashMap<>(label.getFont().getAttributes());
                    attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                    label.setFont(new Font(attributes));
                }

                label.setForeground(taskColors.getOrDefault(task, Color.LIGHT_GRAY));
                label.setBackground(isSelected ? new Color(70, 130, 180) : new Color(45, 45, 45));
                label.setOpaque(true);
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBackground(new Color(45, 45, 45));
        scrollPane.getViewport().setBackground(new Color(45, 45, 45));
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 30, 30)); // Match dark mode
        JTextField taskInputField = new JTextField();
        taskInputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        taskInputField.setBackground(new Color(45, 45, 45));
        taskInputField.setForeground(Color.LIGHT_GRAY);
        taskInputField.setCaretColor(Color.WHITE);
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addButton.setBackground(new Color(70, 130, 180)); // blue button
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        // Load tasks from file
        loadTasksFromFile(taskListModel);

        addButton.addActionListener(e -> {
            String task = taskInputField.getText().trim();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskInputField.setText("");
                saveTasksToFile(taskListModel); // Save tasks after adding
            }
        });

        // Context menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem doneItem = new JMenuItem("Done");
        JMenuItem removeItem = new JMenuItem("Remove");
        JMenuItem colorItem = new JMenuItem("Change Color");

        popupMenu.add(doneItem);
        popupMenu.add(removeItem);
        popupMenu.add(colorItem);

        taskList.setComponentPopupMenu(popupMenu);

        doneItem.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String task = taskListModel.get(selectedIndex);
                if (!task.endsWith(" (Done)")) {
                    taskListModel.set(selectedIndex, task + " (Done)");
                    saveTasksToFile(taskListModel); // Save tasks after marking as done
                }
            }
        });

        removeItem.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String task = taskListModel.get(selectedIndex);
                taskColors.remove(task);
                taskListModel.remove(selectedIndex);
                saveTasksToFile(taskListModel); // Save tasks after removing
            }
        });

        colorItem.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String task = taskListModel.get(selectedIndex);
                Color selectedColor = JColorChooser.showDialog(this, "Choose Task Color", taskColors.getOrDefault(task, Color.LIGHT_GRAY));
                if (selectedColor != null) {
                    taskColors.put(task, selectedColor);
                    taskList.repaint();
                }
            }
        });
    }

    // Save tasks to a file
    private void saveTasksToFile(DefaultListModel<String> taskListModel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FILE))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load tasks from a file
    private void loadTasksFromFile(DefaultListModel<String> taskListModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TASKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                taskListModel.addElement(line);
            }
        } catch (IOException e) {
            // if no files to load
            System.out.println("No existing tasks to load.");
        }
    }
}
